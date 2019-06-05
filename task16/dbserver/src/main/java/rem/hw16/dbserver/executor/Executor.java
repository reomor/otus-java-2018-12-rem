package rem.hw16.dbserver.executor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public class Executor {
    public static <T> T query(SessionFactory sessionFactory, Function<Session, T> function) {
        try(Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            final T result = function.apply(session);
            session.flush();
            transaction.commit();
            return result;
        }
    }

    public static void update(SessionFactory sessionFactory, Consumer<Session> sessionConsumer) {
        try(Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            sessionConsumer.accept(session);
            transaction.commit();
        }
    }
}
