package rem.hw16.messageserver.server.ws;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WsMessagingAdapter extends WebSocketAdapter {
    private static final Logger logger = Logger.getLogger(WsMessagingAdapter.class.getName());
    private Session session;

    @Override
    public void onWebSocketConnect(Session session) {
        logger.log(Level.INFO, "Connect: " + session);
        this.session = session;
    }

    @Override
    public void onWebSocketText(String message) {
        System.out.println("text: " + message);
        logger.log(Level.INFO, "text: " + message);
        try {
            //echo
            this.session.getRemote().sendString(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        cause.printStackTrace();
        System.out.println("Error: " + cause.getLocalizedMessage());
    }
}
