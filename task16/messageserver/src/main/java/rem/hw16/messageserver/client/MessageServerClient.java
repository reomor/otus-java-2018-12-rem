package rem.hw16.messageserver.client;

import rem.hw16.messageserver.core.Address;

import java.io.IOException;

public interface MessageServerClient {
    Address register(String prefix);

    Address requestCompanion(String prefix);

    void startClientLoop();

    SocketClient getSocketClient();

    Address getAddressTo();

    Address getAddressFrom();

    void close() throws IOException;
}
