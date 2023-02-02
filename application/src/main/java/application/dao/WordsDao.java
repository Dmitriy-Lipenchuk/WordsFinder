package application.dao;

import application.exceptions.NonCyrillicStringException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.WordsHelper.checkIfCyrillic;
import static utils.WordsHelper.countLetters;

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

    private static final String CHECK_IF_WORD_EXISTS_IN_SUGGESTED_WORDS = """
            SELECT word FROM suggested_words
            WHERE word = ?
            """;

    public WordsDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(@NotNull String word) {
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
            throw new NonCyrillicStringException();
        }

        List<String> words = new ArrayList<>();

        word = word.toLowerCase();

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
        if (!checkIfCyrillic(word)) {
            throw new NonCyrillicStringException();
        }

        word = word.toLowerCase();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CHECK_IF_WORD_EXISTS);
            statement.setString(1, word);

            PreparedStatement secondStatement = connection.prepareStatement(CHECK_IF_WORD_EXISTS_IN_SUGGESTED_WORDS);
            secondStatement.setString(1, word);

            ResultSet resultSet = statement.executeQuery();
            ResultSet secondResultSet = secondStatement.executeQuery();

            return resultSet.next() || secondResultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
