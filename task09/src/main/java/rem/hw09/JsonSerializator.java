package rem.hw09;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class JsonSerializator {
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
            System.out.println("is class");
        }
        return "";
    }

    private Object elementToJson(Object element) {
        if (element instanceof Number || element instanceof Boolean || element instanceof String || element instanceof Character) {
            return element;
        } else {
            return toJson(element);
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
        ((Map) map).forEach((keyObject, valueObject) -> {
            jsonObject.put(elementToJson(keyObject), elementToJson(valueObject));
        });
        return jsonObject;
    }
}
