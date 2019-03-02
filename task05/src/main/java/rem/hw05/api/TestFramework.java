package rem.hw05.api;

import rem.hw05.api.annotation.*;
import rem.hw05.api.reflection.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestFramework {
    public static void runTests(String clazzName) {
        try {
            Class clazz = Class.forName(clazzName);
            runTests(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void runTests(Class<?> clazz) {
        for (Method beforeAllMethod: ReflectionHelper.getStaticMethodsByAnnotation(clazz, BeforeAll.class)) {
            try {
                beforeAllMethod.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        for (Method testMethod : ReflectionHelper.getMethodsByAnnotation(clazz, Test.class)) {
            Object testInstance = ReflectionHelper.instantiate(clazz);
            for (Method method : ReflectionHelper.getMethodsByAnnotation(clazz, BeforeEach.class)) {
                ReflectionHelper.callMethod(testInstance, method.getName());
            }
            ReflectionHelper.callMethod(testInstance, testMethod.getName());
            for (Method method : ReflectionHelper.getMethodsByAnnotation(clazz, AfterEach.class)) {
                ReflectionHelper.callMethod(testInstance, method.getName());
            }
        }
        for (Method afterAllMethod: ReflectionHelper.getStaticMethodsByAnnotation(clazz, AfterAll.class)) {
            try {
                afterAllMethod.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
