package rem.hw16.messageServer.client;

import rem.hw16.messageServer.core.Message;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketClientImpl implements SocketClient {
    private static final int WORKERS_COUNT = 2;

    private final Socket socket;
    private final ExecutorService executorService;

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>();

    public SocketClientImpl(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    public SocketClientImpl(Socket socket) {
        this.socket = socket;
        this.executorService = Executors.newFixedThreadPool(WORKERS_COUNT);
        executorService.submit(this::sendMessage);
        executorService.submit(this::receiveMessage);
    }

    @Override
    public Message poll() {
        return input.poll();
    }

    @Override
    public Message take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void send(Message message) {
        output.add(message);
    }

    @Override
    public void close() throws IOException {
        socket.close();
        executorService.shutdown();
    }

    private void sendMessage() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            while (socket.isConnected()) {
                final Message message = output.take();
                oos.writeObject(message);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            while (socket.isConnected()) {
                final Message message = (Message) ois.readObject();
                input.add(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
