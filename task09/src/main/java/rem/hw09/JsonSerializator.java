package rem.hw09;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rem.hw09.reflection.ReflectionHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
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
            System.out.println("is map");
        } else {
            System.out.println("is class");
        }
        return "";
    }

    private JSONArray arrayToJson(Object array) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < Array.getLength(array); i++) {
            Object element = Array.get(array, i);
            if (element instanceof Number || element instanceof Boolean || element instanceof String || element instanceof Character) {
                jsonArray.add(element);
            } else {
                final String e = toJson(element);
                jsonArray.add(e);
            }
        }
        return jsonArray;
    }

    private JSONArray collectionToJson(Object collection) {
        final Object[] array = ((Collection) collection).toArray();
        return arrayToJson(array);
    }

}
