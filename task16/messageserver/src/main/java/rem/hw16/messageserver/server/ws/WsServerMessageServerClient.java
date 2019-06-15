package rem.hw16.messageserver.server.ws;

import rem.hw16.messageserver.client.MessageServerClientImpl;

public class WsServerMessageServerClient extends MessageServerClientImpl {

    public WsServerMessageServerClient(String host, int port) {
        super(host, port, "WS", "DB");
    }

    @Override
    public void startClientLoop() {
        initClient();
        System.out.println("wsClientLoop");
    }
}
