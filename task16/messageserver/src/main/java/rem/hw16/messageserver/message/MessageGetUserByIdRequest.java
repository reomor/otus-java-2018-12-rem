package rem.hw16.messageserver.message;

import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.core.AddressedMessage;

public class MessageGetUserByIdRequest extends AddressedMessage {
    private final Long id;

    public MessageGetUserByIdRequest(Address from, Address to, Long id) {
        super(from, to);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
