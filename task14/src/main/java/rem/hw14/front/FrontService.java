package rem.hw14.front;

import rem.hw14.messaging.core.Addressee;

public interface FrontService extends Addressee {
    void handleRequest(String name);
}
