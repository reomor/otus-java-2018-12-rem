package rem.hw16.messageserver;

import rem.hw16.messageserver.server.WebSocketServer;

public class WsServerMain {
    // mvn jetty:run as war

    public static void main(String[] args) {
        WebSocketServer socketServer = new WebSocketServer();
        socketServer.start();
    }
}
