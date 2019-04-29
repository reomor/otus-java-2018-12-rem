package rem.hw14.executor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

    public static void update(SessionFactory sessionFactory, SessionHandler function) {
        try(Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            function.handle(session);
            transaction.commit();
        }
    }
}
