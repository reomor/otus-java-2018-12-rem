package rem.hw05.api;

import rem.hw05.api.annotation.AfterEach;
import rem.hw05.api.annotation.BeforeEach;
import rem.hw05.api.annotation.Test;
import rem.hw05.api.reflection.ReflectionHelper;

import java.lang.reflect.Method;

public class TestFramework {
    public static void runTests(Class<?> clazz) {
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
    }
}
