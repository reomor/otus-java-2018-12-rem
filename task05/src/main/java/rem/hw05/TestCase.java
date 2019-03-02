package rem.hw05;

import rem.hw05.api.annotation.BeforeEach;
import rem.hw05.api.annotation.Test;

public class TestCase {
    @BeforeEach
    public void before() {
        System.out.println("before each");
    }

    @Test
    public void test01() {
        System.out.println("tes01");
    }
}
