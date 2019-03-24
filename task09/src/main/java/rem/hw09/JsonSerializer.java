package rem.hw09;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rem.hw09.reflection.ReflectionHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonSerializer {
    public String toJson(Object object) {
        if (object == null) {
            return "null";
        }
        final Class<?> objectClass = object.getClass();
        if (object instanceof Number) {
            return String.valueOf(object);
        } else if (object instanceof String || object instanceof Character) {
            return "\"" + String.valueOf(object) + "\"";
        } else if (object instanceof Boolean) {
            return String.valueOf(object);
        } else if (objectClass.isArray()) {
            return arrayToJson(object).toJSONString();
        } else if (object instanceof Collection) {
            return collectionToJson(object).toJSONString();
        } else if (object instanceof Map) {
            return mapToJson(object).toJSONString();
        } else {
            return JSONObject.toJSONString(objectToJson(object));
        }
    }

    private Object innerToJson(Object object) {
        if (object == null) {
            return "null";
        }
        final Class<?> objectClass = object.getClass();
        if (object instanceof Number) {
            return String.valueOf(object);
        } else if (object instanceof String || object instanceof Character) {
            return String.valueOf(object);
        } else if (object instanceof Boolean) {
            return String.valueOf(object);
        } else if (objectClass.isArray()) {
            return arrayToJson(object);
        } else if (object instanceof Collection) {
            return collectionToJson(object);
        } else if (object instanceof Map) {
            return mapToJson(object);
        } else {
            return objectToJson(object);
        }
    }

    private Object elementToJson(Object element) {
        if (element instanceof Number || element instanceof Boolean) {
            return element;
        } else {
            return innerToJson(element);
        }
    }

    private JSONArray arrayToJson(Object array) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < Array.getLength(array); i++) {
            Object element = Array.get(array, i);
            final Object elementToJson = elementToJson(element);
            jsonArray.add(elementToJson);
        }
        return jsonArray;
    }

    private JSONArray collectionToJson(Object collection) {
        final Object[] array = ((Collection) collection).toArray();
        return arrayToJson(array);
    }

    private JSONObject mapToJson(Object map) {
        JSONObject jsonObject = new JSONObject();
        ((Map) map).forEach((keyObject, valueObject) ->
                jsonObject.put(elementToJson(keyObject), elementToJson(valueObject)));
        return jsonObject;
    }

    /**
     * Because of inheritance JSONObject from HashMap the order of records is absent
     * then we use LinkedHashMap
     */
    private Map<Object, Object> objectToJson(Object object) {
        final Map<Object, Object> map = new LinkedHashMap<>();
        Class<?> objectClass = object.getClass();
        while (objectClass != null && objectClass != Object.class) {
            final Field[] declaredFields = objectClass.getDeclaredFields();
            for (Field field : declaredFields) {
                if (Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                final Object fieldValue = ReflectionHelper.getFieldValue(object, field);
                if (fieldValue != null) {
                    map.put(field.getName(), elementToJson(fieldValue));
                }
            }
            objectClass = objectClass.getSuperclass();
        }
        return map;
    }
}
