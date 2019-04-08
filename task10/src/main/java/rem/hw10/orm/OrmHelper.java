package rem.hw10.orm;

import rem.hw10.domain.DataSet;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrmHelper {

    private OrmHelper() {
    }

    public static <T extends DataSet> String getEntityInsertStatement(T dataSet) {
        final EntityDefinition entityDefinition = OrmEntityDefinitionCache.getInstance()
                .getDataSetEntityDefinition(dataSet.getClass());
        return entityDefinition.insertStatement(dataSet);
    }

    public static <T extends DataSet> String getEntitySelectStatement(long id, Class<T> clazz) {
        final EntityDefinition entityDefinition = OrmEntityDefinitionCache.getInstance()
                .getDataSetEntityDefinition(clazz);
        return entityDefinition.selectByIdStatement(id);
    }

    public static <T extends DataSet> String getEntityInsertPrepareStatement(T dataSet) {
        final EntityDefinition entityDefinition = OrmEntityDefinitionCache.getInstance()
                .getDataSetEntityDefinition(dataSet.getClass());
        return entityDefinition.insertPreparedStatement();
    }

    public static <T extends DataSet> String getEntitySelectPrepareStatement(long id, Class<T> clazz) {
        final EntityDefinition entityDefinition = OrmEntityDefinitionCache.getInstance()
                .getDataSetEntityDefinition(clazz);
        return entityDefinition.selectByIdPreparedStatement(id);
    }

    public static <T extends DataSet> String getSelectStatement(Class<T> clazz) {
        final EntityDefinition entityDefinition = OrmEntityDefinitionCache.getInstance()
                .getDataSetEntityDefinition(clazz);
        return entityDefinition.selectStatement();
    }

    public static <T extends DataSet> List<T> extractList(ResultSet resultSet, Class<T> clazz) throws SQLException {
        List<T> objectList = new ArrayList<>();
        final List<Field> objectFieldsList = ReflectionHelper.getObjectFieldsList(clazz);
        while (resultSet.next()) {
            T object = ReflectionHelper.instantiate(clazz);
            for (Field field : objectFieldsList) {
                Object rowValue = getObjectForField(resultSet, field);
                ReflectionHelper.setFieldValue(object, field, rowValue);
            }
            objectList.add(object);
        }
        return objectList;
    }

    public static EntityDefinition getObjectEntityDefinition(Class<? extends DataSet> clazz) {
        return OrmEntityDefinitionCache.getInstance()
                .getDataSetEntityDefinition(clazz);
    }

    private static Object getObjectForField(ResultSet resultSet, Field field) throws SQLException {
        Class<?> fieldType = field.getType();
        final String fieldName = field.getName();
        if (Long.class.equals(fieldType)) {
            return resultSet.getLong(fieldName);
        }
        return resultSet.getObject(fieldName);
    }

}
