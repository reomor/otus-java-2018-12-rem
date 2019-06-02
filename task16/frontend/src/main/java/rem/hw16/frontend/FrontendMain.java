package rem.hw16.frontend;

import rem.hw16.messageServer.client.SocketClient;
import rem.hw16.messageServer.client.SocketClientImpl;
import rem.hw16.messageServer.core.Address;
import rem.hw16.messageServer.message.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendMain {
    private static final Logger logger = Logger.getLogger(FrontendMain.class.getName());
    private static final String HOST = "localhost";
    private static final int PORT = 6000;

    private Address addressFrom;
    private Address addressTo;

    public static void main(String[] args) throws IOException {
        new FrontendMain().start();
    }

    public void start() throws IOException {
        SocketClient socketClient = new SocketClientImpl(HOST, PORT);
        // wait for self addressFrom
        socketClient.send(new MessageRegisterRequest("FRONT"));
        try {
            final MessageRegisterResponse message = (MessageRegisterResponse) socketClient.take();
            addressFrom = message.getAddress();
            logger.log(Level.INFO, "Got addressFrom from server: " + addressFrom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // wait for DB addressTo
        try {
            MessageCompanionResponse message;
            do {
                socketClient.send(new MessageCompanionRequest("DB"));
                message = (MessageCompanionResponse) socketClient.take();
                if (message.getAddress() != null) {
                    addressTo = message.getAddress();
                }
                Thread.sleep(100);
            } while (message.getAddress() == null);
            logger.log(Level.INFO, "Got addressTo from server: " + addressTo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        socketClient.send(new TestMessage(addressFrom, addressTo));
        try {
            Thread.sleep(50_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketClient.close();
    }
}
