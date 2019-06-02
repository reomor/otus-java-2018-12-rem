package rem.hw16.messageServer.client;

import rem.hw16.messageServer.core.Message;

import java.io.IOException;

public interface SocketClient {
    Message poll();

    Message take() throws InterruptedException;

    void send(Message message);

    void close() throws IOException;
}
