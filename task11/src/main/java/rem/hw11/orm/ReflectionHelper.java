package rem.hw11.orm;

import org.reflections.Reflections;
import rem.hw11.annotation.DataSetEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"SameParameterValue", "BooleanVariableAlwaysNegated"})
public final class ReflectionHelper {

    private ReflectionHelper() {
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                final Constructor<T> constructor = type.getDeclaredConstructor();
                return constructor.newInstance();
            } else {
                final Class<?>[] classes = toClasses(args);
                final Constructor<T> constructor = type.getDeclaredConstructor(classes);
                return constructor.newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldValue(Object object, Field field) {
        boolean isAccessible = true;
        try {
            isAccessible = field.canAccess(object);
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public static <T> List<Field> getObjectFieldsList(Class<T> clazz) {
        List<Field> fieldList = new ArrayList<>();
        Class currentClazz = clazz;
        while (currentClazz != null && !Object.class.equals(currentClazz)) {
            fieldList.addAll(Arrays.asList(currentClazz.getDeclaredFields()));
            currentClazz = currentClazz.getSuperclass();
        }
        return fieldList;
    }

    public static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.canAccess(object);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    public static void setFieldValue(Object object, Field field, Object value) {
        boolean isAccessible = true;
        try {
            isAccessible = field.canAccess(object);
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    public static Object callMethod(Object object, String name, Object... args) {
        Method method = null;
        boolean isAccessible = true;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            isAccessible = method.canAccess(object);
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }
        return null;
    }

    public static Method[] getStaticMethodsByAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(annotationClazz))
                .filter(method -> (method.getModifiers() & Modifier.STATIC) != 0)
                .toArray(Method[]::new);
    }

    public static Method[] getMethodsByAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(annotationClazz))
                .toArray(Method[]::new);
    }

    public static Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotationClazz) {
        Reflections reflections = new Reflections("rem.hw10");
        return reflections.getTypesAnnotatedWith(DataSetEntity.class);
    }

    private static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);
    }
}
