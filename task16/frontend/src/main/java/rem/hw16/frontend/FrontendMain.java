package rem.hw16.frontend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rem.hw16.dbserver.domain.AddressDataSet;
import rem.hw16.dbserver.domain.UserDataSet;
import rem.hw16.messageserver.client.MessageServerClient;
import rem.hw16.messageserver.client.MessageServerClientImpl;
import rem.hw16.messageserver.client.SocketClient;
import rem.hw16.messageserver.client.SocketClientImpl;
import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.message.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendMain {
    private static final Logger logger = Logger.getLogger(FrontendMain.class.getName());
    private static final String HOST = "localhost";
    private static final int PORT = 6000;
    private static final Gson gson = new GsonBuilder().create();

    private Address addressFrom;
    private Address addressTo;

    public static void main(String[] args) throws IOException {
        new FrontendMain().start();
    }

    public void start() throws IOException {
        SocketClient socketClient = new SocketClientImpl(HOST, PORT);
        MessageServerClient messageServerClient = new MessageServerClientImpl(socketClient);
        // wait for self addressFrom
        addressFrom = messageServerClient.register("FRONT");
        // wait for DB addressTo
        addressTo = messageServerClient.getCompanion("DB");
        //
        //socketClient.send(new TestMessage(addressFrom, addressTo));
        final UserDataSet userDataSet = new UserDataSet("First", 1, new AddressDataSet("Lenina, 1"));
        socketClient.send(new MessageJsonWithClass(addressFrom, addressTo, gson.toJson(userDataSet), userDataSet.getClass()));
        try {
            Thread.sleep(50_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketClient.close();
    }
}
