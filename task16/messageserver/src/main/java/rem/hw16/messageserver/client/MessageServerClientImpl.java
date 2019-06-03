package rem.hw16.messageserver.client;

import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.message.MessageCompanionRequest;
import rem.hw16.messageserver.message.MessageCompanionResponse;
import rem.hw16.messageserver.message.MessageRegisterRequest;
import rem.hw16.messageserver.message.MessageRegisterResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServerClientImpl implements MessageServerClient {
    private static final Logger logger = Logger.getLogger(MessageServerClient.class.getName());
    private final SocketClient socketClient;

    public MessageServerClientImpl(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    public SocketClient getSocketClient() {
        return socketClient;
    }

    @Override
    public Address register(String prefix) {
        Address addressFrom = null;
        socketClient.send(new MessageRegisterRequest(prefix));
        try {
            final MessageRegisterResponse message = (MessageRegisterResponse) socketClient.take();
            addressFrom = message.getAddress();
            logger.log(Level.INFO, "Got addressFrom from server: " + addressFrom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return addressFrom;
    }

    @Override
    public Address getCompanion(String prefix) {
        Address addressTo = null;
        try {
            MessageCompanionResponse message;
            do {
                socketClient.send(new MessageCompanionRequest(prefix));
                message = (MessageCompanionResponse) socketClient.take();
                if (message.getAddress() != null) {
                    addressTo = message.getAddress();
                }
                Thread.sleep(1000);
            } while (message.getAddress() == null);
            logger.log(Level.INFO, "Got addressTo from server: " + addressTo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return addressTo;
    }
}
