package rem.hw11.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import rem.hw11.dbcommon.DDLService;

public class DDLServiceImpl implements DDLService {
    private final static String TRUNCATE_TABLES = "TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK";
    private final SessionFactory sessionFactory;

    public DDLServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createTables() {
        throw new UnsupportedOperationException("\"createTables\" operation is not supported");
    }

    @Override
    public void deleteTables() {
        try (Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.createNativeQuery(TRUNCATE_TABLES).executeUpdate();
            transaction.commit();
        }
    }
}
