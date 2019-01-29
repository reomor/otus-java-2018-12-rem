package rem.hw02;

import java.util.ArrayList;
import java.util.Random;

import static rem.hw02.MyAgent.getObjectSize;

public class BasicTest {
//& 'C:\Program Files\Java\jdk1.8.0_144\bin\java.exe' -javaagent:..\..\agent\target\agent.jar -jar .\app-1.0-SNAPSHOT.jar
    public static void main(String[] args) throws Exception {
        Random random = new Random();
        while (true) {
            System.out.println("Sleeping...");
            int initialCapacity = random.nextInt(100);
            System.out.println("Initial size is: " + initialCapacity);
            System.out.println(getObjectSize(initialCapacity));
            System.out.println(getObjectSize(new ArrayList<Integer>(initialCapacity)));
            Thread.sleep(1000);
        }
    }
}
