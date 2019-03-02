package rem.hw05;

import rem.hw05.api.annotation.*;

public class TestCase {
    @BeforeAll
    public static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("after all");
    }

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
