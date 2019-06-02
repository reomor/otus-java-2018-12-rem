package rem.hw16.messageserver.message;

import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.core.AddressedMessage;

public class MessageJson extends AddressedMessage {
    private final String jsonMessage;

    public MessageJson(Address from, Address to, String jsonMessage) {
        super(from, to);
        this.jsonMessage = jsonMessage;
    }

    public String getJsonMessage() {
        return jsonMessage;
    }
}
