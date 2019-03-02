package rem.hw05;

import rem.hw05.api.annotation.AfterEach;
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

    @Test
    public void test02() {
        System.out.println("tes02");
    }

    @AfterEach
    public void after() {
        System.out.println("after each");
    }
}
