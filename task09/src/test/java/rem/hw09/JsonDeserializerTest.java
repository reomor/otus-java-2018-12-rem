package rem.hw09;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JsonDeserializerTest {
    private JsonDeserializer deserializer;

    @BeforeEach
    public void setUp() {
        deserializer = new JsonDeserializer();
    }

    @Test
    public void intTest() {
        final int expected = 1;
        final String jsonString = "1";
        final Integer actual = deserializer.fromJson(jsonString, Integer.class);
        assertThat(actual, Is.is(expected));
    }

    @Test
    public void arrayTest() {
        final int[] expected = {1, 2, 3, 4};
        final String jsonString = "[1,2,3,4]";
        Integer[] actual = deserializer.fromJson(jsonString, Integer[].class);
        assertThat(actual, Is.is(expected));
    }

    @Test
    public void arrayListTest() {
        final List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        final String jsonString = "[1,2,3]";
        List actual = deserializer.fromJson(jsonString, expected.getClass(), new Class[]{Integer.class});
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void setTest() {
        final Set<Character> expected = new HashSet<>();
        expected.add('A');
        expected.add('B');
        expected.add('C');
        final String jsonString = "[\"A\",\"B\",\"C\"]";
        final Set actual = deserializer.fromJson(jsonString, expected.getClass(), new Class[]{Character.class});
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void mapTest() {
        final Map<String, Integer> map = new HashMap<>();
        map.put("s1", 1);
        map.put("s2", 2);
        map.put("s3", 3);
        final String jsonString = "{\"s1\":\"2value\",\"1key\":\"1value\"}";
        deserializer.fromJson(jsonString, HashMap.class, null);
    }

    @Test
    public void objectTest() {
        final AA expected = new AA(1);
        final String jsonString = "{\"innerI\":1}";
        AA actual = deserializer.fromJson(jsonString, AA.class);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    public void complexObjectTest() {
        final A expected = new A();
        final String jsonString = "{\"i\":0,\"string\":\"123\",\"array\":[1.2,1.3],\"set\":[1,2,3],\"queue\":[true],\"map\":{\"2key\":\"2value\",\"1key\":\"1value\"},\"mapI\":{\"1\":1,\"2\":22},\"c\":\"a\",\"innerI\":1}";
        A actual = deserializer.fromJson(jsonString, A.class);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }
}