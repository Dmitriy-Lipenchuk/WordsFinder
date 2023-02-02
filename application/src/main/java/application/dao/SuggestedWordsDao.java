package application.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class SuggestedWordsDao {
    private final DataSource dataSource;

    @Autowired
    public SuggestedWordsDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_INTO_SUGGESTED_WORDS = """
            INSERT INTO suggested_words (word)
            VALUES (?)
            """;

    public void create(@NotNull String word) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_SUGGESTED_WORDS);

            statement.setString(1, word);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
