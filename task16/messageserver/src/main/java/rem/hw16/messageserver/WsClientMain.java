package rem.hw16.messageserver;

import rem.hw16.messageserver.client.WsMessageServerClient;
import rem.hw16.messageserver.client.WsMessageServerClientImpl;

import java.net.URISyntaxException;

public class WsClientMain {
    public static void main(String[] args) throws URISyntaxException {
        WsMessageServerClient wsMessageServerClient = new WsMessageServerClientImpl("ws://localhost:8080/wsmserver") {
            @Override
            public void startClientLoop() {
                while (true) {
                    getWebSocketClient().sendMessage("msg");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };
        wsMessageServerClient.startClientLoop();
    }
}
