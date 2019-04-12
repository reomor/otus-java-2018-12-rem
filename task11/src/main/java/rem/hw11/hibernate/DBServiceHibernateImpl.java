package rem.hw11.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import rem.hw11.dao.DataSetDao;
import rem.hw11.dao.UserDataSetHibernateDao;
import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.DataSet;
import rem.hw11.domain.UserDataSet;

import java.sql.SQLException;
import java.util.List;

public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;
    private final DataSetDao<UserDataSet> dataSetDao;

    public DBServiceHibernateImpl() {
        Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

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
        dataSetDao = new UserDataSetHibernateDao(sessionFactory);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        final ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public String getConnectionMetaData() throws SQLException {
        return null;
    }

    @Override
    public void save(DataSet dataSetEntity) throws SQLException {

    }

    @Override
    public DataSet load(long id) throws SQLException {
        return null;
    }

    @Override
    public List loadAll() throws SQLException {
        return null;
    }


    @Override
    public void createTables() throws SQLException {
        throw new UnsupportedOperationException("\"createTables\" operation is not supported");
    }

    @Override
    public void deleteTables() throws SQLException {
        throw new UnsupportedOperationException("\"deleteTables\" operation is not supported");
    }

    @Override
    public Class getType() {
        return null;
    }
}
