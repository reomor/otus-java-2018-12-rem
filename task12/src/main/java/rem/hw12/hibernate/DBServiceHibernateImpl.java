package rem.hw12.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;
import rem.hw12.dao.DataSetDao;
import rem.hw12.dbcommon.ConnectionHelper;
import rem.hw12.dbcommon.DBService;
import rem.hw12.domain.AddressDataSet;
import rem.hw12.domain.PhoneDataSet;
import rem.hw12.domain.UserDataSet;

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

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
        configuration.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:mymemdb");
        configuration.setProperty("hibernate.connection.username", "SA");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");

        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
        dataSetDao = new UserDataSetHibernateDaoImpl(sessionFactory);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        final ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
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
