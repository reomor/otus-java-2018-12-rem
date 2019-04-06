package rem.hw10.dao;

import rem.hw10.domain.DataSet;
import rem.hw10.executor.Executor;
import rem.hw10.orm.OrmHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDataSetDao implements DataSetDao {
    private final Connection connection;

    public UserDataSetDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T dataSetEntity) throws SQLException {
        final String insertStatement = OrmHelper.getEntityInsertStatement(dataSetEntity);
        Executor.update(connection, insertStatement);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        final String selectStatement = OrmHelper.getEntitySelectStatement(id, clazz);
        return Executor.query(connection, selectStatement, resultSet -> {
            final List<T> objectList = OrmHelper.extractList(resultSet, clazz);
            if (objectList.size() != 1) {
                throw new RuntimeException("Non unique result or no at all (number of records: " + objectList.size() + ")");
            }
            return objectList.get(0);
        });
    }

    @Override
    public <T extends DataSet> List<T> loadAll(Class<T> clazz) throws SQLException {
        final String selectStatement = OrmHelper.getSelectStatement(clazz);
        return Executor.query(connection, selectStatement, resultSet ->
                OrmHelper.extractList(resultSet, clazz));
    }
}
