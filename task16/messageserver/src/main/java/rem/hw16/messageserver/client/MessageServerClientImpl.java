package rem.hw16.messageserver.client;

import rem.hw16.messageserver.client.base.SocketClient;
import rem.hw16.messageserver.client.base.SocketClientImpl;
import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.message.MessageCompanionRequest;
import rem.hw16.messageserver.message.MessageCompanionResponse;
import rem.hw16.messageserver.message.MessageRegisterRequest;
import rem.hw16.messageserver.message.MessageRegisterResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MessageServerClientImpl implements MessageServerClient {
    private static final Logger logger = Logger.getLogger(MessageServerClient.class.getName());

    private SocketClient socketClient;
    private final String prefix;
    private final String companionPrefix;
    private Address addressTo;
    private Address addressFrom;

    public MessageServerClientImpl(String host, int port, String prefix) {
        this(host, port, prefix, "");
    }

    public MessageServerClientImpl(String host, int port, String prefix, String companionPrefix) {
        try {
            this.socketClient = new SocketClientImpl(host, port);
            this.prefix = prefix;
            this.companionPrefix = companionPrefix;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot establish connection host(" + host + "), port(" + port + ")");
            throw new RuntimeException(e);
        }
    }

    public void initClientRegisterAndRequestCompanion() {
        // wait for self addressFrom
        this.addressFrom = register(prefix);
        // wait for companion addressTo
        this.addressTo = requestCompanion(companionPrefix);
    }

    public void initClientRegister() {
        this.addressFrom = register(prefix);
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
            logger.log(Level.INFO, "Got address from server: " + addressFrom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return addressFrom;
    }

    @Override
    public Address requestCompanion(String prefix) {
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

    @Override
    public Address getAddressTo() {
        return addressTo;
    }

    @Override
    public Address getAddressFrom() {
        return addressFrom;
    }

    @Override
    public void close() throws IOException {
        socketClient.close();
    }
}
