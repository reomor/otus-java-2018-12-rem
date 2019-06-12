package rem.hw16.dbserver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rem.hw16.dbserver.client.DBServiceMessageServerClient;

public class DBServerMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final DBServiceMessageServerClient dbServiceMessageServerClient =
                context.getBean("dbServiceMessageServerClient", DBServiceMessageServerClient.class);
        dbServiceMessageServerClient.startClientLoop();
    }
}
