package rem.hw11.dao;

import rem.hw11.domain.DataSet;
import rem.hw11.exception.BaseDaoException;
import rem.hw11.executor.Executor;
import rem.hw11.orm.EntityDefinition;
import rem.hw11.orm.OrmHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDataSetDao implements DataSetDao {
    private final Connection connection;

    public UserDataSetDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T dataSetEntity) throws SQLException {
        final String insertStatement = OrmHelper.getEntityInsertPrepareStatement(dataSetEntity);
        PreparedStatement prepareStatement = connection.prepareStatement(insertStatement);
        final EntityDefinition objectEntityDefinition = OrmHelper.getObjectEntityDefinition(dataSetEntity.getClass());
        final Map<Field, Object> fieldsWithValuesMap = objectEntityDefinition.getFieldsWithValuesMap(dataSetEntity, "id");
        int parameterIndex = 1;
        for (Map.Entry<Field, Object> entry : fieldsWithValuesMap.entrySet()) {
            prepareStatement.setObject(parameterIndex++, entry.getValue());
        }
        Executor.update(prepareStatement);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        final String selectStatement = OrmHelper.getEntitySelectPrepareStatement(id, clazz);
        PreparedStatement prepareStatement = connection.prepareStatement(selectStatement);
        prepareStatement.setLong(1, id);
        return Executor.query(prepareStatement, resultSet -> {
            final List<T> objectList = OrmHelper.extractList(resultSet, clazz);
            if (objectList.size() != 1) {
                throw new BaseDaoException("Non unique result or empty (number of records: " + objectList.size() + ")");
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
