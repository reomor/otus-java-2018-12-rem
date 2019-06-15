package rem.hw16.frontend.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import rem.hw16.dbserver.domain.UserDataSet;
import rem.hw16.frontend.front.FrontService;
import rem.hw16.messageserver.client.MessageServerClientImpl;
import rem.hw16.messageserver.core.Message;
import rem.hw16.messageserver.message.MessageGetUserByIdResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendMessageServerClient extends MessageServerClientImpl {
    private static final Logger logger = Logger.getLogger(FrontendMessageServerClient.class.getName());
    private static final Gson gson = new GsonBuilder().create();

    private final ExecutorService executorService;
    private FrontService frontService;

    public FrontendMessageServerClient(String host, int port) {
        super(host, port, "FRONT", "DB");
        this.executorService = Executors.newFixedThreadPool(1);
        startClientLoop();
    }

    @Override
    public void startClientLoop() {
        executorService.submit(this::initClient);
        executorService.submit(() -> {
            do {
                try {
                    final Message message = getSocketClient().take();
                    if (message instanceof MessageGetUserByIdResponse) {
                        final MessageGetUserByIdResponse messageJson = (MessageGetUserByIdResponse) message;
                        logger.log(Level.INFO, "Got message (" + messageJson.getClass() + ")");
                        final Class<?> jsonObjectClass;
                        try {
                            jsonObjectClass = Class.forName(messageJson.getClazz());
                            final Object fromJsonObject = gson.fromJson(messageJson.getJsonMessage(), jsonObjectClass);
                            frontService.addUserData((UserDataSet) fromJsonObject);
                        } catch (ClassNotFoundException e) {
                            logger.log(Level.WARNING, "Object in message(" + messageJson + ") has wrong type(" + messageJson.getClazz() + ")");
                        }
                    } else {
                        logger.log(Level.WARNING, "Message type is unknown(" + message.getClass() + ")");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!executorService.isShutdown());
        });
    }

    @Override
    public void close() throws IOException {
        super.close();
        executorService.shutdown();
    }

    @Lazy
    @Autowired
    public void setFrontService(FrontService frontService) {
        this.frontService = frontService;
    }
}
