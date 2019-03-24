package rem.hw09;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class JsonZerializerTest {
    private JsonSerializer serializer;

    @BeforeEach
    public void setUp() {
        serializer = new JsonSerializer();
    }

    @Test
    public void intTest() {
        final int primitive = 1;
        final String jsonStringExpected = "1";
        final String jsonStringActual = serializer.toJson(primitive);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void intBoxTest() {
        final Integer primitive = 1;
        final String jsonStringExpected = "1";
        final String jsonStringActual = serializer.toJson(primitive);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void byteTest() {
        final byte primitive = 1;
        final String jsonStringExpected = "1";
        final String jsonStringActual = serializer.toJson(primitive);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void byteBoxTest() {
        final Byte primitive = 1;
        final String jsonStringExpected = "1";
        final String jsonStringActual = serializer.toJson(primitive);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void boolTest() {
        final boolean bool = true;
        final String jsonStringExpected = "true";
        final String jsonStringActual = serializer.toJson(bool);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void boolBoxTest() {
        final Boolean bool = true;
        final String jsonStringExpected = "true";
        final String jsonStringActual = serializer.toJson(bool);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void stringTest() {
        final String string = "test string";
        final String jsonStringExpected = "\"" + string + "\"";
        final String jsonStringActual = serializer.toJson(string);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void charTest() {
        final  char character = 'c';
        final String jsonStringExpected = "\"" + character + "\"";
        final String jsonStringActual = serializer.toJson(character);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void charBoxTest() {
        final  Character character = new Character('c');
        final String jsonStringExpected = "\"" + character + "\"";
        final String jsonStringActual = serializer.toJson(character);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void customClassTest() {
        Gson gson = new Gson();
        final AA customClass = new AA(1);
        final String jsonStringExpected = gson.toJson(customClass);
        final String jsonStringActual = serializer.toJson(customClass);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void customClassWithInheritanceTest() {
        Gson gson = new Gson();
        final A customClass = new A();
        final String jsonStringExpected = gson.toJson(customClass);
        final String jsonStringActual = serializer.toJson(customClass);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void arrayPrimitiveTest() {
        final int[] array = {1, 2, 3, 4, 5};
        final String jsonStringExpected = "[1,2,3,4,5]";
        final String jsonStringActual = serializer.toJson(array);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void arrayStringTest() {
        final String[] array = {"1", "2", "3"};
        final String jsonStringExpected = "[\"1\",\"2\",\"3\"]";
        final String jsonStringActual = serializer.toJson(array);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void arrayClassTest() {
        final AA[] array = {new AA(1), new AA(2)};
        final String jsonStringExpected = "[{\"innerI\":1},{\"innerI\":2}]";
        final String jsonStringActual = serializer.toJson(array);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void listPrimitiveTest() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        final String jsonStringExpected = "[1,2]";
        final String jsonStringActual = serializer.toJson(list);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void setPrimitiveTest() {
        Set<String> set = new HashSet<>();
        set.add("str1");
        set.add("str2");
        final String jsonStringExpected = "[\"str1\",\"str2\"]";
        final String jsonStringActual = serializer.toJson(set);
        assertEquals(jsonStringExpected, jsonStringActual);
    }

    @Test
    public void mapPrimitiveTest() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 22);
        map.put(3, 333);
        final String jsonStringExpected = "{\"1\":1,\"2\":22,\"3\":333}";
        final String jsonStringActual = serializer.toJson(map);
        assertEquals(jsonStringExpected, jsonStringActual);
    }
}
