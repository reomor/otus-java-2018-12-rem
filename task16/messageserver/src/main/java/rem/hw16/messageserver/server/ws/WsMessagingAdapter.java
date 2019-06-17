package rem.hw16.messageserver.server.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import rem.hw16.messageserver.message.MessageGetUserByIdRequest;

import java.io.IOException;
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
        this.wsServerMessageServerClient = new WsServerMessageServerClient("localhost", 6000, session);
        this.wsServerMessageServerClient.initClientRegisterAndRequestCompanion();
        this.wsServerMessageServerClient.startClientLoop();
    }

    @Override
    public void onWebSocketText(String message) {
        logger.log(Level.INFO, "Got message from ui: " + message);
        final JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        final JsonElement typeElement = jsonObject.get("type");
        if (typeElement != null) {
            final String type = jsonObject.get("type").getAsString();
            logger.log(Level.INFO, "type: " + type);
            if ("getbyid".equals(type)) {
                wsServerMessageServerClient.getSocketClient().send(
                        new MessageGetUserByIdRequest(
                                wsServerMessageServerClient.getAddressFrom(),
                                wsServerMessageServerClient.getAddressTo(),
                                jsonObject.get("id").getAsLong()
                        )
                );
            } else {
                logger.log(Level.WARNING, "Unknown message type: " + type);
            }
        } else {
            logger.log(Level.WARNING, "Message doesn't have type: " + message);
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
        try {
            wsServerMessageServerClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        System.out.println("Error: " + cause.getLocalizedMessage());
    }
}
