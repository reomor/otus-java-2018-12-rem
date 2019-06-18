package rem.hw16.messageserver.server.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import rem.hw16.messageserver.client.MessageServerClientImpl;
import rem.hw16.messageserver.core.Message;
import rem.hw16.messageserver.message.MessageGetUserByIdResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WsServerMessageServerClient extends MessageServerClientImpl {
    private static final Logger logger = Logger.getLogger(WsServerMessageServerClient.class.getName());
    private static final Gson gson = new GsonBuilder().create();

    private final Session session;
    private final ExecutorService executorService;

    public WsServerMessageServerClient(String host, int port, Session session) {
        super(host, port, "WS", "DB");
        this.session = session;
        this.executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public void startClientLoop() {
        logger.log(Level.INFO, "Started websocket client loop");
        executorService.submit(this::initClientRegisterAndRequestCompanion);
        executorService.submit(() -> {
            do {
                try {
                    final Message message = getSocketClient().take();
                    logger.log(Level.INFO, "Got message: " + message);
                    sendMessage(message);
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "Error(" + e.getLocalizedMessage() + " during message handle and sending");
                }
            } while (true);
        });
    }

    private void sendMessage(Message message) {
        if (message instanceof MessageGetUserByIdResponse) {
            final MessageGetUserByIdResponse messageGetUserByIdResponse = (MessageGetUserByIdResponse) message;
            logger.log(Level.INFO, "Got message: " + messageGetUserByIdResponse);
            try {
                final JsonObject jsonObject = gson.fromJson(messageGetUserByIdResponse.getJsonMessage(), JsonObject.class);
                jsonObject.addProperty("type", "getbyid");
                logger.log(Level.INFO, jsonObject.toString());
                session.getRemote().sendString(jsonObject.toString());
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error during sending json to ui " + e.getLocalizedMessage());
            }
        } else {
            logger.log(Level.WARNING, "Unknown message type: " + message);
        }
    }
}
