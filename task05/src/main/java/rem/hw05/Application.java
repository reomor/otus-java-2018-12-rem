package rem.hw05;

import rem.hw05.api.TestFramework;

public class Application {
    public static void main(String[] args) {
        TestFramework.runTests(TestCase.class);
    }
}
