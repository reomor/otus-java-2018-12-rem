package rem.hw11.executor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExecutorHibernate {
    public static <T> T query(SessionFactory sessionFactory, Function<Session, T> function) {
        try(Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            final T result = function.apply(session);
            session.flush();
            transaction.commit();
            return result;
        }
    }

    public static void update(SessionFactory sessionFactory, Consumer<Session> function) {
        try(Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            function.accept(session);
            transaction.commit();
        }
    }
}
