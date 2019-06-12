package rem.hw16.messageserver.client;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class WsClientMain {
    public static void main(String[] args) {
        try {
            String dest = "ws://localhost:8080/wsmserver";
            WsSocketClient wsSocketClient = new WsSocketClientImpl();
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(wsSocketClient, new URI(dest));
            while (true) {
                Thread.sleep(500);
                wsSocketClient.sendMessage("message");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
