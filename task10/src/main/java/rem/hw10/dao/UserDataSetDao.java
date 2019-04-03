package rem.hw10.dao;

import rem.hw10.domain.DataSet;
import rem.hw10.executor.Executor;
import rem.hw10.orm.OrmHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDataSetDao implements DataSetDao {
    private final Connection connection;

    public UserDataSetDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T dataSetEntity) throws SQLException {
        final String entityInsertStatement = OrmHelper.getEntityInsertStatement(dataSetEntity);
        Executor.update(connection, entityInsertStatement);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return null;
    }
}
