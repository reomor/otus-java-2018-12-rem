package rem.hw16.messageserver.client;

import rem.hw16.messageserver.core.Address;

public interface MessageServerClient {
    Address register(String prefix);

    Address getCompanion(String prefix);
}
