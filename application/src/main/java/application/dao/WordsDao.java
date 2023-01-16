package application.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static utils.WordsHelper.*;

@Component
public final class WordsDao {

    private final DataSource dataSource;

    private static final String INSERT_INTO_WORDS = """
            INSERT INTO words (word, a, b, v, g, d, e, je, z, i, ji, k, l, m, n, o, p, r, s, t, u, f, h, ce, ch, sh, she, tvd, yi, mgk, ee, yu, ya,yo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_ALL_WORDS_WITH_SAME_LETTERS = """
            SELECT word FROM words
            WHERE a <= ?
            AND b <= ?
            AND v <= ?
            AND g <= ?
            AND d <= ?
            AND e <= ?
            AND je <= ?
            AND z <= ?
            AND i <= ?
            AND ji <= ?
            AND k <= ?
            AND l <= ?
            AND m <= ?
            AND n <= ?
            AND o <= ?
            AND p <= ?
            AND r <= ?
            AND s <= ?
            AND t <= ?
            AND u <= ?
            AND f <= ?
            AND h <= ?
            AND ce <= ?
            AND ch <= ?
            AND sh <= ?
            AND she <= ?
            AND tvd <= ?
            AND yi <= ?
            AND mgk <= ?
            AND ee <= ?
            AND yu <= ?
            AND ya <= ?
            AND yo <= ?
            """;

    private static final String CHECK_IF_WORD_EXISTS = """
            SELECT word FROM words
            WHERE word = ?
            """;

    public WordsDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(@NotNull String word) {
        word = getUtf8String(word);

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_WORDS);
            int[] letters = countLetters(word);

            statement.setString(1, word);

            for (int i = 0, j = 2; i < letters.length; i++, j++) {
                statement.setInt(j, letters[i]);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getWordsWithSameLetters(@NotNull String word) {
        if (!checkIfCyrillic(word)) {
            String wrongInputMessage = "\u0412\u0432\u0435\u0434\u0451\u043D\u043E\u0435 \u0441\u043B\u043E\u0432\u043E \u043D\u0435 \u0434\u043E\u043B\u0436\u043D\u043E \u0441\u043E\u0434\u0435\u0440\u0436\u0430\u0442\u044C \u043D\u0438\u0447\u0435\u0433\u043E \u043A\u0440\u043E\u043C\u0435 \u043A\u0438\u0440\u0438\u043B\u043B\u0438\u0446\u044B";
            return List.of(wrongInputMessage); // "Введёное слово не должно содержать ничего кроме кириллицы"
        }

        List<String> words = new ArrayList<>();
        word = getUtf8String(word)
                .toLowerCase(Locale.ROOT);

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WORDS_WITH_SAME_LETTERS);
            int[] letters = countLetters(word);

            for (int i = 0, j = 1; i < letters.length; i++, j++) {
                statement.setInt(j, letters[i]);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                words.add(resultSet.getString("word"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return words;
    }

    public  boolean checkIfWordExists(@NotNull String word) {
        // TODO: - Сделать исключения для случаев когда пользователь вводит некоретные данные + добавить для них хендлеры
        if (!checkIfCyrillic(word)) {
            return true;
        }

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CHECK_IF_WORD_EXISTS);
            statement.setString(1, word);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
