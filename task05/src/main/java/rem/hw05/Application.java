package rem.hw05;

import java.util.Arrays;
import java.util.function.IntFunction;

public class Application {

    public static void main(String[] args) {
        Object[] argz = new Object[]{1, "abc", 3.0, 'a'};
        Class<?>[] classes = Arrays.stream(argz).map(s -> s.getClass()).toArray(value -> new Class<?>[value]);
        //Class<?>[] classezz = Arrays.stream(argz).map(s -> s.getClass()).toArray(value -> new Class[value]);
        System.out.println();
        final Class<?>[] classez = new Class<?>[argz.length];
        for (int i = 0; i < argz.length; i++) {
            classez[i] = argz[i].getClass();
        }
        System.out.println();
    }
}
