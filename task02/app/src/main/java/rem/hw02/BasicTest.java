package rem.hw02;

public class BasicTest {
    //& 'C:\Program Files\Java\jdk1.8.0_144\bin\java.exe' -javaagent:..\..\agent\target\agent.jar -jar .\app-1.0-SNAPSHOT.jar
    public static void main(String[] args) throws Exception {
        Report reportStrings = new Measure(AbstractObjectFactory.getFactory(FactoryObject.STRING)).measureAbstractFactory();
        reportStrings.print();

        Report reportObjects = new Measure(AbstractObjectFactory.getFactory(FactoryObject.OBJECT)).measureAbstractFactory();
        reportObjects.print();

        Report reportIntegers = new Measure(AbstractObjectFactory.getFactory(FactoryObject.INTEGER)).measureAbstractFactory();
        reportIntegers.print();
    }
}
