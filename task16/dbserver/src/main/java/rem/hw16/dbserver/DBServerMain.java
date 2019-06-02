package rem.hw16.dbserver;

import rem.hw16.messageServer.client.SocketClient;
import rem.hw16.messageServer.client.SocketClientImpl;
import rem.hw16.messageServer.core.Address;
import rem.hw16.messageServer.message.MessageRegisterRequest;
import rem.hw16.messageServer.message.MessageRegisterResponse;
import rem.hw16.messageServer.message.TestMessage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServerMain {
    private static final Logger logger = Logger.getLogger(DBServerMain.class.getName());
    private static final String HOST = "localhost";
    private static final int PORT = 6000;

    private Address address;

    public static void main(String[] args) throws IOException {
        new DBServerMain().start();
    }

    public void start() throws IOException {
        SocketClient socketClient = new SocketClientImpl(HOST, PORT);
        socketClient.send(new MessageRegisterRequest("DB"));
        try {
            final MessageRegisterResponse message = (MessageRegisterResponse) socketClient.take();
            address = message.getAddress();
            logger.log(Level.INFO, "Got address from server: " + address);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketClient.send(new TestMessage(address, new Address("another")));
        try {
            Thread.sleep(50_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketClient.close();
    }
}
