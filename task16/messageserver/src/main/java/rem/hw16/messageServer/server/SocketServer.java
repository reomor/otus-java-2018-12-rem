package rem.hw16.messageServer.server;

import rem.hw16.messageServer.client.SocketClient;
import rem.hw16.messageServer.client.SocketClientImpl;
import rem.hw16.messageServer.core.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SocketServer implements SocketServerMBean {
    private static final int PORT = 6000;
    private final List<SocketClient> clientList;

    public SocketServer() {
        this.clientList = new CopyOnWriteArrayList<>();
    }

    public void start() throws IOException, InterruptedException {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                SocketClient socketClient = new SocketClientImpl(socket);
                clientList.add(socketClient);
                final Message message = socketClient.take();
                System.out.println(message.getClass());
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
            /*NOP*/
        }
    }
}
