package rem.hw16.messageServer.server;

import rem.hw16.messageServer.client.SocketClient;
import rem.hw16.messageServer.client.SocketClientImpl;
import rem.hw16.messageServer.core.Address;
import rem.hw16.messageServer.core.Message;
import rem.hw16.messageServer.message.MessageRegisterRequest;
import rem.hw16.messageServer.message.MessageRegisterResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Map<Address, SocketClient> addressMap;

    public SocketServer() {
        this.executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        this.clientList = new CopyOnWriteArrayList<>();
        this.addressMap = new HashMap<>();
    }

    public void start() throws IOException {
        executorService.submit(this::serverLoop);
        logger.log(Level.INFO, "Socket server started. Waiting for connections ...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!executorService.isShutdown()) {
                Socket socket = serverSocket.accept();
                SocketClient socketClient = new SocketClientImpl(socket);
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
                    addressMap.put(address, socketClient);
                    socketClient.send(new MessageRegisterResponse(address));
                }
                //else
                //TODO resend message to addressTo
                logger.log(Level.INFO, "Message: " + message);
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
