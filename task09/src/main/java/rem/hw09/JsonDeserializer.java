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
    /**
     * Reads class T object from jsonString
     * @param jsonString - JSON string object representation
     * @param clazz - target class
     * @param <T> - generic target class parameter
     * @return class T object or null if error
     */
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

    /**
     * Reads class T object from jsonString
     * @param jsonString - JSON string object representation
     * @param clazz - target class
     * @param componentClass array with generic classes
     * @param <T> - generic target class parameter
     * @return class T object with generic parameters or null if error
     */
    public <T> T fromJson(String jsonString, Class<T> clazz, Type[] componentClass) {
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
            return jsonToArrayOrCollection(object, clazz);
        } else if (object instanceof JSONObject) {
            return jsonToObject(object, clazz);
        } else {
            throw new IllegalArgumentException("object class in unsupported");
        }
    }

    private <T> T innerFromJson(Object object, Class<T> clazz, Type[] componentTypes) {
        if (object == null) {
            return null;
        } else if (object instanceof JSONArray) {
            return jsonToGenericCollection(object, clazz, componentTypes);
        } else if (object instanceof JSONObject) {
            if (Map.class.isAssignableFrom(clazz)) {
                return jsonToMap(object, clazz, componentTypes);
            } else {
                return jsonToObject(object, clazz);
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
            jsonArray.forEach(element ->
                    arrayObjectCollection.add(innerFromJson(element, Object.class))
            );
        }
        return arrayObject;
    }

    private <T> T jsonToGenericCollection(Object object, Class<T> clazz, Type[] componentTypes) {
        JSONArray jsonArray = (JSONArray) object;
        T arrayObject = null;
        if (Collection.class.isAssignableFrom(clazz)) {
            arrayObject = ReflectionHelper.instantiate(clazz);
            Collection arrayObjectCollection = (Collection) arrayObject;
            jsonArray.forEach(element -> {
                try {
                    final Class<?> componentClass = Class.forName(componentTypes[0].getTypeName());
                    arrayObjectCollection.add(innerFromJson(element, componentClass));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        return arrayObject;
    }

    private <T> T jsonToMap(Object object, Class<T> clazz, Type[] componentTypes) {
        JSONObject jsonObject = (JSONObject) object;
        T mapObject = ReflectionHelper.instantiate(clazz);
        if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) mapObject;
            jsonObject.forEach((fieldKeyObj, fieldValueObj) -> {
                try {
                    Class<?> keyClass = (componentTypes != null && componentTypes.length >= 1) ? Class.forName(componentTypes[0].getTypeName()) : Object.class;
                    Class<?> valueClass = (componentTypes != null && componentTypes.length >= 2) ? Class.forName(componentTypes[1].getTypeName()) : Object.class;
                    final Object key = convertObjToClass(fieldKeyObj, keyClass);
                    final Object value = convertObjToClass(fieldValueObj, valueClass);
                    map.put(key, value);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        return mapObject;
    }

    private <T> Object convertObjToClass(Object object, Class<T> clazz) {
        if (object instanceof String) {
            if (Number.class.isAssignableFrom(clazz)) {
                return convertStringToNumber((String) object, clazz);
            }
        }
        if (Number.class.isAssignableFrom(object.getClass())) {
            return jsonToInt(object, clazz);
        }
        return object;
    }

    private <T> T convertStringToNumber(String string, Class<T> clazz) {
        if (!Number.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("is not a number");
        }
        Long value = Long.valueOf(string);
        return jsonToInt(value, clazz);
    }

    private <T> T jsonToObject(Object object, Class<T> clazz) {
        JSONObject jsonObject = (JSONObject) object;
        T objectNew = ReflectionHelper.instantiate(clazz);
        jsonObject.forEach((fieldNameObj, fieldValueObj) -> {
            String fieldName = (String) fieldNameObj;
            Field declaredField = null;
            Class<?> current = clazz;
            while (true) {
                try {
                    declaredField = current.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e) {
                    current = current.getSuperclass();
                }
            }
            final Type genericFieldType = declaredField.getGenericType();
            Type[] fieldComponentTypes = null;
            if (genericFieldType instanceof ParameterizedType) {
                fieldComponentTypes = ((ParameterizedType) genericFieldType).getActualTypeArguments();
            }
            Class type = ReflectionHelper.getFieldValue(objectNew, declaredField).getClass();
            Object value = null;
            if (fieldComponentTypes == null) {
                value = innerFromJson(fieldValueObj, type);
            } else {
                value = innerFromJson(fieldValueObj, type, fieldComponentTypes);
            }
            ReflectionHelper.setFieldValue(objectNew, declaredField, value);
        });
        return objectNew;
    }

}
