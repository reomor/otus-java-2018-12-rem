package rem.hw14.messaging.core;

public interface Addressee {
    void init();

    Address getAddress();

    MessageSystem getMS();
}
