package rem.hw16.messageserver.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.stereotype.Component;
import rem.hw16.messageserver.server.ws.WsMessagingServlet;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class WebSocketServer {
    private static final Logger logger = Logger.getLogger(WebSocketServer.class.getName());

    public void start() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.addServlet(WsMessagingServlet.class, "/wsmserver");

        server.setHandler(handler);

        try {
            server.start();
            logger.log(Level.INFO, "Websocket server started. Waiting for connections ...");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
