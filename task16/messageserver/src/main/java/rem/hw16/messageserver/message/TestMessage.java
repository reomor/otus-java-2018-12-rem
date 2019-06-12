package rem.hw16.messageserver.message;

import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.core.AddressedMessage;

public class TestMessage extends AddressedMessage {
    private final String payload = "Test message";

    public TestMessage(Address from, Address to) {
        super(from, to);
    }

    public String getPayload() {
        return payload;
    }
}
