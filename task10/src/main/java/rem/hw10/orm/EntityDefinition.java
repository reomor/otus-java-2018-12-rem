package rem.hw10.orm;

import rem.hw10.domain.DataSet;

import java.lang.reflect.Field;
import java.util.List;
import java.util.StringJoiner;

public class EntityDefinition {
    private Class<? extends DataSet> dataSetClazz;
    private String tableName;
    private List<Field> classFieldList;

    public EntityDefinition(Class<? extends DataSet> clazz) {
        this.dataSetClazz = clazz;
        tableName = clazz.getSimpleName().toLowerCase();
        classFieldList = ReflectionHelper.getObjectFieldsList(clazz);
    }

    public List<Field> getClassFieldList() {
        return classFieldList;
    }

    public String getJoinedFieldNames() {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Field field : classFieldList) {
            stringJoiner.add(field.getName());
        }
        return stringJoiner.toString();
    }

    public String getJoinedFieldValues() {
        StringJoiner fieldsValues = new StringJoiner(",");
        for (Field declaredField : classFieldList) {
            final Object fieldValue = ReflectionHelper.getFieldValue(dataSetClazz, declaredField);
            if (fieldValue == null) {
                continue;
            }
            String stringFieldValue = String.valueOf(fieldValue);
            if (declaredField.getType().equals(String.class) || declaredField.getType().equals(Character.class)) {
                fieldsValues.add("'" + stringFieldValue + "'");
            } else {
                fieldsValues.add(stringFieldValue);
            }
        }
        return fieldsValues.toString();
    }

    public String getTableName() {
        return tableName;
    }
}
