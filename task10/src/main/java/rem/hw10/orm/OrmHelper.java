package rem.hw10.orm;

import rem.hw10.domain.DataSet;

import java.lang.reflect.Field;
import java.util.StringJoiner;

public class OrmHelper {
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
}
