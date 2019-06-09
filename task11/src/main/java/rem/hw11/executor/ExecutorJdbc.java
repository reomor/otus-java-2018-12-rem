package rem.hw11.executor;

import java.sql.*;

public class ExecutorJdbc {
    public static <T> T query(Connection connection, String query, TResultSetHandler<T> handler) throws SQLException {
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

    public static <T> T query(PreparedStatement preparedStatement, TResultSetHandler<T> handler) throws SQLException {
        try (preparedStatement) {
            final ResultSet result = preparedStatement.executeQuery();
            return handler.handle(result);
        }
    }

    public static void update(PreparedStatement preparedStatement) throws SQLException {
        try(preparedStatement) {
            preparedStatement.executeUpdate();
        }
    }
}
