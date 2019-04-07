package rem.hw10.orm;

import rem.hw10.domain.DataSet;

import java.lang.reflect.Field;
import java.util.*;

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

    public String getJoinedFieldNames(String ... ignored) {
        Set<String> ignoredSet = new HashSet<>(Arrays.asList(ignored));
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Field field : classFieldList) {
            final String fieldName = field.getName();
            if (ignoredSet.contains(fieldName)) {
                continue;
            }
            stringJoiner.add(fieldName);
        }
        return stringJoiner.toString();
    }

    public String getJoinedFieldValues(DataSet dataSet, String ... ignored) {
        Set<String> ignoredSet = new HashSet<>(Arrays.asList(ignored));
        StringJoiner fieldsValues = new StringJoiner(",");
        for (Field field : classFieldList) {
            final Object fieldValue = ReflectionHelper.getFieldValue(dataSet, field);
            if (fieldValue == null || ignoredSet.contains(field.getName())) {
                continue;
            }
            String stringFieldValue = String.valueOf(fieldValue);
            if (field.getType().equals(String.class) || field.getType().equals(Character.class)) {
                fieldsValues.add("'" + stringFieldValue + "'");
            } else {
                fieldsValues.add(stringFieldValue);
            }
        }
        return fieldsValues.toString();
    }

    private String getJoinedPreparedFieldValues(String ... ignored) {
        final int ignoredSize = ignored.length;
        StringJoiner stringJoiner = new StringJoiner(",");
        for (int i = 0; i < getClassFieldList().size() - ignoredSize; i++) {
            stringJoiner.add("?");
        }
        return stringJoiner.toString();
    }

    public String getTableName() {
        return tableName;
    }

    public String selectStatement() {
        return String.format("SELECT %s FROM PUBLIC.%s",
                getJoinedFieldNames(),
                getTableName());
    }

    public String selectByIdStatement(long id) {
        return String.format("SELECT %s FROM PUBLIC.%s WHERE id=%d",
                getJoinedFieldNames(),
                getTableName(),
                id);
    }

    public String insertStatement(DataSet dataSet) {
        return String.format("INSERT INTO PUBLIC.%s (%s) VALUES (%s)",
                getTableName(),
                getJoinedFieldNames("id"),
                getJoinedFieldValues(dataSet, "id"));
    }

    public String selectByIdPreparedStatement(long id) {
        return "";
    }

    public String insertPreparedStatement() {
        return "";
    }
}
