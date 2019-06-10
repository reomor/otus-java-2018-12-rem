package rem.hw11.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import rem.hw11.dao.DataSetDao;
import rem.hw11.dbcommon.ConnectionHelper;
import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.UserDataSet;
import rem.hw11.exception.DBServiceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBServiceHibernateImpl implements DBService<UserDataSet> {
    private final SessionFactory sessionFactory;
    private final DataSetDao<UserDataSet> dataSetDao;

    public DBServiceHibernateImpl(SessionFactory sessionFactory, DataSetDao<UserDataSet> dataSetDao) {
        this.sessionFactory = sessionFactory;
        this.dataSetDao = dataSetDao;
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
