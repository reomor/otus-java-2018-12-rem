package rem.hw11.dao;

import rem.hw11.domain.UserDataSet;
import rem.hw11.exception.BaseDaoException;
import rem.hw11.executor.Executor;
import rem.hw11.myorm.EntityDefinition;
import rem.hw11.myorm.OrmHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDataSetMyOrmDao implements DataSetDao<UserDataSet> {
    private final Connection connection;

    public UserDataSetMyOrmDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(UserDataSet dataSetEntity) throws SQLException {
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
    public UserDataSet load(long id) throws SQLException {
        final Class<UserDataSet> clazz = getType();
        final String selectStatement = OrmHelper.getEntitySelectPrepareStatement(id, clazz);
        PreparedStatement prepareStatement = connection.prepareStatement(selectStatement);
        prepareStatement.setLong(1, id);
        return Executor.query(prepareStatement, resultSet -> {
            final List<UserDataSet> objectList = OrmHelper.extractList(resultSet, clazz);
            if (objectList.size() != 1) {
                throw new BaseDaoException("Non unique result or empty (number of records: " + objectList.size() + ")");
            }
            return objectList.get(0);
        });
    }

    @Override
    public List<UserDataSet> loadAll() throws SQLException {
        final Class<UserDataSet> clazz = getType();
        final String selectStatement = OrmHelper.getSelectStatement(clazz);
        return Executor.query(connection, selectStatement, resultSet ->
                OrmHelper.extractList(resultSet, clazz));
    }

    @Override
    public Class<UserDataSet> getType() {
        return UserDataSet.class;
    }
}
