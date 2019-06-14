package rem.hw16.messageserver.client;

import rem.hw16.messageserver.client.base.WebSocketClient;

public interface WsMessageServerClient {
    void startClientLoop();

    WebSocketClient getWebSocketClient();
}
