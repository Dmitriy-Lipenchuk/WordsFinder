package utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

final class WordsFromFileToDb {

    private WordsFromFileToDb() {
    }

    private static final String INSERT_INTO_WORDS = """
            INSERT INTO words (word, a, b, v, g, d, e, je, z, i, ji, k, l, m, n, o, p, r, s, t, u, f, h, ce, ch, sh, she, tvd, yi, mgk, ee, yu, ya,yo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static void insertWordsBatchedFromFile(@NotNull Path path) throws IOException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_WORDS);

            Files.lines(path)
                    .map(WordsHelper::getUtf8StringFromFile)
                    .forEach(word -> {
                        int[] letters = WordsHelper.countLetters(word);
                        try {
                            statement.setString(1, word);

                            for (int i = 0, j = 2; i < letters.length; i++, j++) {
                                statement.setInt(j, letters[i]);
                            }

                            statement.addBatch();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
