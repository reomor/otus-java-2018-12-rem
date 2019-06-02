package rem.hw16.dbserver;

import rem.hw16.messageServer.client.SocketClient;
import rem.hw16.messageServer.client.SocketClientImpl;
import rem.hw16.messageServer.core.Address;
import rem.hw16.messageServer.message.TestMessage;

import java.io.IOException;

public class DBServerMain {
    private static final String HOST = "localhost";
    private static final int PORT = 6000;

    public static void main(String[] args) throws IOException {
        new DBServerMain().start();
    }

    public void start() throws IOException {
        SocketClient socketClient = new SocketClientImpl(HOST, PORT);
        socketClient.send(new TestMessage(new Address("DB"), new Address("FRONT")));
        try {
            Thread.sleep(50_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketClient.close();
    }
}
