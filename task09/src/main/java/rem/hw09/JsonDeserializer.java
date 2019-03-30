package rem.hw09;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rem.hw09.reflection.ReflectionHelper;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;

public class JsonDeserializer {
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        JSONParser parser = new JSONParser();
        try {
            final Object parsedObject = parser.parse(jsonString);
            return innerFromJson(parsedObject, clazz);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T fromJson(String jsonString, Class<T> clazz, Class<?>[] componentClass) {
        JSONParser parser = new JSONParser();
        try {
            final Object parsedObject = parser.parse(jsonString);
            return innerFromJson(parsedObject, clazz, componentClass);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T innerFromJson(Object object, Class<T> clazz) {
        if (object == null) {
            return null;
        } else if (object instanceof Long) {
            return jsonToInt(object, clazz);
        } else if (object instanceof Double) {
            return jsonToDouble(object, clazz);
        } else if (object instanceof String) {
            return jsonToString(object, clazz);
        } else if (object instanceof Boolean) {
            return jsonToBoolean(object, clazz);
        } else if (object instanceof JSONArray) {
            System.out.println("is array or collection");
            return jsonToArrayOrCollection(object, clazz);
        } else if (object instanceof JSONObject) {
            System.out.println("is class or map");
            return jsonToObject(object, clazz, null);
        } else {
            throw new IllegalArgumentException("object class in unsupported");
        }
    }

    private <T> T innerFromJson(Object object, Class<T> clazz, Class[] componentClass) {
        if (object == null) {
            return null;
        } else if (object instanceof JSONArray) {
            return jsonToGenericCollection(object, clazz, componentClass);
        } else if (object instanceof JSONObject) {
            System.out.println("json object");
            if (Map.class.isAssignableFrom(clazz)) {
                System.out.println("is map");
                return jsonToMap(object, clazz, componentClass);
            } else {
                System.out.println("another class");
                return jsonToObject(object, clazz, componentClass);
            }
        } else {
            throw new IllegalArgumentException("object class is unsupported");
        }
    }

    private <T> T jsonToInt(Object object, Class<T> clazz) {
        Long value = (Long) object;
        if (Byte.class.equals(clazz) || byte.class.equals(clazz)) {
            return (T) Byte.valueOf(value.byteValue());
        } else if (Short.class.equals(clazz) || short.class.equals(clazz)) {
            return (T) Short.valueOf(value.shortValue());
        } else if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
            return (T) Integer.valueOf(value.intValue());
        } else {
            return (T) value;
        }
    }

    private <T> T jsonToDouble(Object object, Class<T> clazz) {
        Double value = (Double) object;
        if (Float.class.equals(clazz) || float.class.equals(clazz)) {
            return (T) Float.valueOf(value.floatValue());
        } else {
            return (T) value;
        }
    }

    private <T> T jsonToString(Object object, Class<T> clazz) {
        String value = (String) object;
        if ((Character.class.equals(clazz) || char.class.equals(clazz)) && value.length() == 1) {
            return (T) Character.valueOf(value.charAt(0));
        } else {
            return (T) value;
        }
    }

    private <T> T jsonToBoolean(Object object, Class<T> clazz) {
        Boolean value = (Boolean) object;
        if (Boolean.class.equals(clazz) || boolean.class.equals(clazz)) {
            return (T) value;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private <T> T jsonToArrayOrCollection(Object object, Class<T> clazz) {
        JSONArray jsonArray = (JSONArray) object;
        T arrayObject = null;
        if (clazz.isArray()) {
            arrayObject = (T) Array.newInstance(clazz.getComponentType(), jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                Array.set(arrayObject, i, innerFromJson(jsonArray.get(i), clazz.getComponentType()));
            }
        } else if (Collection.class.isAssignableFrom(clazz)) {
            arrayObject = ReflectionHelper.instantiate(clazz);
            Collection arrayObjectCollection = (Collection) arrayObject;
            Class componentType = null; // find
            /*TODO*/
            jsonArray.forEach(element ->
                    arrayObjectCollection.add(innerFromJson(element, componentType))
            );
        }
        return arrayObject;
    }

    private <T> T jsonToGenericCollection(Object object, Class<T> clazz, Class[] componentClazz) {
        JSONArray jsonArray = (JSONArray) object;
        T arrayObject = null;
        if (Collection.class.isAssignableFrom(clazz)) {
            arrayObject = ReflectionHelper.instantiate(clazz);
            Collection arrayObjectCollection = (Collection) arrayObject;
            jsonArray.forEach(element -> {
                arrayObjectCollection.add(innerFromJson(element, componentClazz[0]));
            });
        }
        return arrayObject;
    }

    private <T> T jsonToMap(Object object, Class<T> clazz, Class[] componentClazz) {
        return null;
    }

    private <T> T jsonToObject(Object object, Class<T> clazz, Class<?>[] componentClazz) {
        JSONObject jsonObject = (JSONObject) object;
        T objectNew = ReflectionHelper.instantiate(clazz);
        jsonObject.forEach((fieldNameObj, fieldValueObj) -> {
            String fieldName = (String) fieldNameObj;
            try {
                Field declaredField = clazz.getDeclaredField(fieldName);
                final Type genericFieldType = declaredField.getGenericType();
                Class<?>[] componentType = null;
                if (genericFieldType instanceof ParameterizedType) {
                    final Type[] actualTypeArguments = ((ParameterizedType) genericFieldType).getActualTypeArguments();
                    componentType = (Class<?>[]) actualTypeArguments;
                }
                //clazz.getSuperclass()
                // get field in superclass
                Class type = ReflectionHelper.getFieldValue(objectNew, declaredField).getClass();
                Object value = null;
                if (componentType == null) {
                    value = innerFromJson(fieldValueObj, type);
                } else {
                    value = innerFromJson(fieldValueObj, type, componentType);
                }
                ReflectionHelper.setFieldValue(objectNew, fieldName, value);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
        return objectNew;
    }

}
