package rem.hw16.messageserver.client;

import rem.hw16.messageserver.client.base.WebSocketClient;
import rem.hw16.messageserver.client.base.WebSocketClientImpl;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class WsMessageServerClientImpl implements WsMessageServerClient {
    private final WebSocketClient webSocketClient;

    public WsMessageServerClientImpl(String destination) throws URISyntaxException {
        webSocketClient = new WebSocketClientImpl();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(webSocketClient, new URI(destination));
        } catch (DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public WebSocketClient getWebSocketClient() {
        return webSocketClient;
    }
}
