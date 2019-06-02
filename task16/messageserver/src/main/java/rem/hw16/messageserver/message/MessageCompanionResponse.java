package rem.hw16.messageserver.message;

import rem.hw16.messageserver.core.Address;
import rem.hw16.messageserver.core.Message;

public class MessageCompanionResponse extends Message {
    private final Address address;

    public MessageCompanionResponse(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "MessageCompanionResponse{" +
                "address=" + address +
                '}';
    }
}
