package rem.hw16.messageserver.client.base;

import rem.hw16.messageserver.core.Message;

import java.io.IOException;

public interface SocketClient {
    Message poll();

    Message take() throws InterruptedException;

    void send(Message message);

    void close() throws IOException;
}
