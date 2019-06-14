package rem.hw16.messageserver.client.base;

import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.core.WsMessage;
import rem.hw16.messageserver.core.coder.WsMessageDecoder;
import rem.hw16.messageserver.core.coder.WsMessageEncoder;

import javax.websocket.*;
import java.io.IOException;

@ClientEndpoint(encoders = WsMessageEncoder.class, decoders = WsMessageDecoder.class)
public class WebSocketClientImpl implements WebSocketClient {
    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("Message received: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println(throwable.getLocalizedMessage());
    }

    @Override
    public void sendMessage(String content) {
        try {
            session.getBasicRemote().sendObject(new WsMessage(new Address("1"), new Address("2"), content));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
