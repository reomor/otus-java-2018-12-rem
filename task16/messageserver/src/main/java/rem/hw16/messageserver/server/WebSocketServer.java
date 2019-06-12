package rem.hw16.messageserver.server;

import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.core.WsMessage;
import rem.hw16.messageserver.core.coder.WsMessageDecoder;
import rem.hw16.messageserver.core.coder.WsMessageEncoder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/wsmserver", encoders = WsMessageEncoder.class, decoders = WsMessageDecoder.class)
public class WebSocketServer implements SocketServerMBean {

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("WebSocket opened: " + session.getId());
        try {
            session.getBasicRemote().sendObject(new WsMessage(new Address("1"), new Address("2"), "Connected " + session.getId()));
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, WsMessage message) throws IOException {
        System.out.println("Message received: " + message);
    }

//    @OnMessage
//    public void onMessage(Session session, String message) throws IOException {
//        System.out.println("Message received: " + message);
//    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            //NOP
        }
    }
}
