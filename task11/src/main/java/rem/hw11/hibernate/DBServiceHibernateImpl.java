package rem.hw11.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import rem.hw11.dao.DataSetDao;
import rem.hw11.dbcommon.ConnectionHelper;
import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.AddressDataSet;
import rem.hw11.domain.PhoneDataSet;
import rem.hw11.domain.UserDataSet;
import rem.hw11.exception.DBServiceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBServiceHibernateImpl implements DBService<UserDataSet> {
    final static String TRUNCATE_TABLES = "TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK";
    private final SessionFactory sessionFactory;
    private final DataSetDao<UserDataSet> dataSetDao;

    public DBServiceHibernateImpl() {
        Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        sessionFactory = configuration.configure().buildSessionFactory();
        dataSetDao = new UserDataSetHibernateDaoImpl(sessionFactory);
    }

    @Override
    public String getConnectionMetaData() {
        try (Connection connection = sessionFactory.getSessionFactoryOptions()
                .getServiceRegistry()
                .getService(ConnectionProvider.class)
                .getConnection()) {
            return ConnectionHelper.getConnectionMetadata(connection);
        } catch (SQLException e) {
            throw new DBServiceException(e.getLocalizedMessage());
        }
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
