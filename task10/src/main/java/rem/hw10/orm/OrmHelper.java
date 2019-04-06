package rem.hw10.orm;

import rem.hw10.domain.DataSet;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static rem.hw10.orm.ReflectionHelper.getObjectFieldsList;

public class OrmHelper {

    private OrmHelper() {}

    public static <T extends DataSet> String getEntityInsertStatement(T dataSet) {
        final Class<? extends DataSet> dataSetClass = dataSet.getClass();
        Class clazz = dataSetClass;
        StringJoiner fields = new StringJoiner(",");
        StringJoiner fieldsValues = new StringJoiner(",");
        while (clazz != null && !Object.class.equals(clazz)) {
            for (Field declaredField : clazz.getDeclaredFields()) {
                final Object fieldValue = ReflectionHelper.getFieldValue(dataSet, declaredField);
                if (fieldValue == null) {
                    continue;
                }
                fields.add(declaredField.getName());
                String stringFieldValue = String.valueOf(fieldValue);
                if (declaredField.getType().equals(String.class) || declaredField.getType().equals(Character.class)) {
                    fieldsValues.add("'" + stringFieldValue + "'");
                } else {
                    fieldsValues.add(stringFieldValue);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return String.format("INSERT INTO PUBLIC.%s (%s) VALUES (%s)", dataSetClass.getSimpleName().toLowerCase(), fields, fieldsValues);
    }

    public static <T extends DataSet> String getEntitySelectStatement(long id, Class<T> clazz) {
        StringJoiner fields = new StringJoiner(",");
        for (Field field : getObjectFieldsList(clazz)) {
            fields.add(field.getName());
        }
        return String.format("SELECT %s FROM PUBLIC.%s WHERE id=%d", fields.toString(), clazz.getSimpleName(), id);
    }

    public static <T extends DataSet> String getSelectStatement(Class<T> clazz) {
        StringJoiner fields = new StringJoiner(",");
        for (Field field : getObjectFieldsList(clazz)) {
            fields.add(field.getName());
        }
        return String.format("SELECT %s FROM PUBLIC.%s", fields.toString(), clazz.getSimpleName());
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

    private static Object getObjectForField(ResultSet resultSet, Field field) throws SQLException {
        Class<?> fieldType = field.getType();
        final String fieldName = field.getName();
        if (Long.class.equals(fieldType)) {
            return resultSet.getLong(fieldName);
        }
        return resultSet.getObject(fieldName);
    }

}
