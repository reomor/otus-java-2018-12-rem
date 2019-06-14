package rem.hw16.messageserver.server;

import rem.hw16.messageserver.client.base.SocketClient;
import rem.hw16.messageserver.client.base.SocketClientImpl;
import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.core.AddressedMessage;
import rem.hw16.messageserver.core.Message;
import rem.hw16.messageserver.message.MessageCompanionRequest;
import rem.hw16.messageserver.message.MessageCompanionResponse;
import rem.hw16.messageserver.message.MessageRegisterRequest;
import rem.hw16.messageserver.message.MessageRegisterResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer implements SocketServerMBean {
    private static final Logger logger = Logger.getLogger(SocketServer.class.getName());
    private static final int PORT = 6000;
    private static final int THREAD_COUNT = 1;

    private final ExecutorService executorService;
    private final List<SocketClient> clientList;
    private final Map<Address, SocketClient> addressSocketClientMap;
    private final Map<String, Deque<Address>> addressMap;

    public SocketServer() {
        this.executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        this.clientList = new CopyOnWriteArrayList<>();
        this.addressSocketClientMap = new HashMap<>();
        this.addressMap = new HashMap<>();
    }

    public void start() throws IOException {
        executorService.submit(this::serverLoop);
        logger.log(Level.INFO, "Socket server started. Waiting for connections ...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!executorService.isShutdown()) {
                Socket socket = serverSocket.accept();
                SocketClientImpl socketClient = new SocketClientImpl(socket);
                socketClient.addCloseObserver(() -> clientList.remove(socketClient));
                clientList.add(socketClient);
                logger.log(Level.INFO, "Client connected: " + socket);
            }
        }
    }

    private void serverLoop() {
        while (true) {
            for (SocketClient socketClient : clientList) {
                final Message message = socketClient.poll();
                if (message == null) continue;
                // register
                if (message instanceof MessageRegisterRequest) {
                    MessageRegisterRequest request = (MessageRegisterRequest) message;
                    logger.log(Level.INFO, "Got register request: " + request);
                    final Address address = new Address(request.getPrefix());
                    //
                    addressMap.putIfAbsent(request.getPrefix(), new ConcurrentLinkedDeque<>());
                    addressMap.get(request.getPrefix()).add(address);
                    addressSocketClientMap.put(address, socketClient);
                    socketClient.send(new MessageRegisterResponse(address));
                    continue;
                } else if (message instanceof MessageCompanionRequest) { //
                    final MessageCompanionRequest request = (MessageCompanionRequest) message;
                    if (addressMap.containsKey(request.getPrefix())) {
                        socketClient.send(new MessageCompanionResponse(addressMap.get(request.getPrefix()).poll()));
                    } else {
                        socketClient.send(new MessageCompanionResponse(null));
                    }
                } else if (message instanceof AddressedMessage) {
                    AddressedMessage addressedMessage = (AddressedMessage) message;
                    logger.log(Level.INFO, "Got message to redirect: " + addressedMessage);
                    if (addressSocketClientMap.containsKey(addressedMessage.getTo())) {
                        addressSocketClientMap.get(addressedMessage.getTo()).send(message);
                    } else {
                        logger.log(Level.WARNING, "Addressee To(" + addressedMessage.getTo() + ") is not known");
                    }
                } else {
                    logger.log(Level.WARNING, "Message type is incorrect(" + message.getClass() + ")");
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            executorService.shutdown();
        }
    }
}
