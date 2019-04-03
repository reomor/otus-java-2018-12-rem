package rem.hw10.executor;

import java.sql.*;

public class Executor {
    public static <T> T query(Connection connection, String query, TResultHandler<T> handler) throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            return handler.handle(result);
        }
    }

    public static void update(Connection connection, String update) throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(update);
        }
    }
}
