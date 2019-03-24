package rem.hw09;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        final int excpected = 1;
        final String jsonString = "1";
        final Integer actual = deserializer.fromJson(jsonString, Integer.class);
        assertThat(actual, Is.is(excpected));
    }
}