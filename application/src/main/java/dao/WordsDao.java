package dao;

import db.utils.ConnectionManager;
import org.jetbrains.annotations.NotNull;
import utils.WordsHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.WordsHelper.*;

public final class WordsDao {
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

    public void insertWord(@NotNull String word) {
        word = getUtf8String(word);

        try (Connection connection = ConnectionManager.open()) {
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
        List<String> words = new ArrayList<>();
        word = getUtf8String(word)
                .replaceAll("\\s+", "")
                .toLowerCase();

        try (Connection connection = ConnectionManager.open()) {
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
}
