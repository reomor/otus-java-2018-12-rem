package rem.hw16.messageserver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rem.hw16.messageserver.server.WebSocketServer;

public class WsServerMain {
    // mvn jetty:run as war

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.start();
    }
}
