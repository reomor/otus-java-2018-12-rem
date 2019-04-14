package rem.hw11.executor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.function.Function;

public class Executor {
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

    public static <T> T query(SessionFactory sessionFactory, Function<Session, T> function) {
        try(Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            final T result = function.apply(session);
            session.flush();
            transaction.commit();
            return result;
        }
    }

    public static void update(SessionFactory sessionFactory, SessionHandler function) {
        try(Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            function.handle(session);
            transaction.commit();
        }
    }
}
