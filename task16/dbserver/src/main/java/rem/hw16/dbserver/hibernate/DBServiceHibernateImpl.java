package rem.hw16.dbserver.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import rem.hw16.dbserver.dao.DataSetDao;
import rem.hw16.dbserver.dbcommon.ConnectionHelper;
import rem.hw16.dbserver.dbcommon.DBService;
import rem.hw16.dbserver.domain.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBServiceHibernateImpl implements DBService<UserDataSet> {
    final static String TRUNCATE_TABLES = "TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK";
    private final SessionFactory sessionFactory;
    private final DataSetDao<UserDataSet> dataSetDao;

    public DBServiceHibernateImpl() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        dataSetDao = new UserDataSetHibernateDaoImpl(sessionFactory);
    }

    @Override
    public String getConnectionMetaData() throws SQLException {
        Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry().
                getService(ConnectionProvider.class).getConnection();
        return ConnectionHelper.getConnectionMetadata(connection);
    }

    @Override
    public void save(UserDataSet dataSetEntity) {
        dataSetDao.save(dataSetEntity);
    }

    @Override
    public UserDataSet load(long id) {
        return dataSetDao.load(id);
    }

    @Override
    public List<UserDataSet> loadAll() {
        return dataSetDao.loadAll();
    }

    @Override
    public void deleteTables() {
        try(Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.createNativeQuery(TRUNCATE_TABLES).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void closeConnection() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public Class getType() {
        return UserDataSet.class;
    }
}
