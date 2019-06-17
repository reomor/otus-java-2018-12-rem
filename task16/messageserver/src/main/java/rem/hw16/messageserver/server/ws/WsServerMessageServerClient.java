package rem.hw16.messageserver.server.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.Session;
import rem.hw16.messageserver.client.MessageServerClientImpl;
import rem.hw16.messageserver.core.Message;

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
        executorService.submit(() -> {
            do {
                try {
                    final Message message = getSocketClient().take();
                    logger.log(Level.INFO, "Got message: " + message);
                    final String jsonString = gson.toJson(message);
                    session.getRemote().sendString(jsonString);
                } catch (InterruptedException | IOException e) {
                    //e.printStackTrace();
                    logger.log(Level.WARNING, "Error(" + e.getLocalizedMessage() + " during message handle and sending");
                }
            } while (true);
        });
    }
}
