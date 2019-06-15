package rem.hw16.messageserver.server.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import rem.hw16.messageserver.message.MessageJson;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WsMessagingAdapter extends WebSocketAdapter {
    private static final Logger logger = Logger.getLogger(WsMessagingAdapter.class.getName());
    private static final Gson gson = new GsonBuilder().create();

    private WsServerMessageServerClient wsServerMessageServerClient;
    private Session session;

    @Override
    public void onWebSocketConnect(Session session) {
        logger.log(Level.INFO, "Connected client: " + session);
        this.session = session;
    }

    @Override
    public void onWebSocketText(String message) {
        System.out.println("text: " + message);
        logger.log(Level.INFO, "text: " + message);
        final JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        final JsonElement type = jsonObject.get("type");
        if (type != null) {
            logger.log(Level.INFO, "type: " + jsonObject.get("type").getAsString());
        } else {
            logger.log(Level.WARNING, "Unknown message type: " + message);
        }
        try {
            //echo
            session.getRemote().sendString(message);
            logger.log(Level.INFO, "Send to socket: " + wsServerMessageServerClient);
            wsServerMessageServerClient.getSocketClient().send(new MessageJson(
                    wsServerMessageServerClient.getAddressFrom(),
                    wsServerMessageServerClient.getAddressTo(),
                    message));
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
        //cause.printStackTrace();
        System.out.println("Error: " + cause.getLocalizedMessage());
    }

    public void setWsServerMessageServerClient(WsServerMessageServerClient wsServerMessageServerClient) {
        this.wsServerMessageServerClient = wsServerMessageServerClient;
    }
}
