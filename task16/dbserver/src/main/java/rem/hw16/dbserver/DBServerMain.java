package rem.hw16.dbserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rem.hw16.messageserver.client.MessageServerClient;
import rem.hw16.messageserver.client.MessageServerClientImpl;
import rem.hw16.messageserver.client.SocketClient;
import rem.hw16.messageserver.client.SocketClientImpl;
import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.core.Message;
import rem.hw16.messageserver.message.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServerMain {
    private static final Logger logger = Logger.getLogger(DBServerMain.class.getName());
    private static final String HOST = "localhost";
    private static final int PORT = 6000;
    private static final Gson gson = new GsonBuilder().create();

    private Address addressFrom;
    private Address addressTo;

    public static void main(String[] args) throws IOException {
        new DBServerMain().start();
    }

    public void start() throws IOException {
        SocketClient socketClient = new SocketClientImpl(HOST, PORT);
        MessageServerClient messageServerClient = new MessageServerClientImpl(socketClient);
        // wait for self addressFrom
        addressFrom = messageServerClient.register("DB");
        // wait for DB addressTo
        addressTo = messageServerClient.getCompanion("FRONT");
        try {
            final Message message = ((MessageServerClientImpl) messageServerClient).getSocketClient().take();
            if (message instanceof MessageJson) {
                MessageJsonWithClass messageJson = (MessageJsonWithClass) message;
                logger.log(Level.INFO, messageJson.getJsonMessage());
                try {
                    final Class<?> jsonObjectClass = Class.forName(messageJson.getClazz());
                    final Object fromJsonObject = gson.fromJson(messageJson.getJsonMessage(), jsonObjectClass);
                    logger.log(Level.INFO, "Class in message: " + messageJson.getClazz() + " class is: " + fromJsonObject.getClass());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(50_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketClient.close();
    }
}
