package rem.hw16.messageServer.message;

import rem.hw16.messageServer.core.Address;
import rem.hw16.messageServer.core.Message;

public class MessageRegisterResponse extends Message {
    private final Address address;

    public MessageRegisterResponse(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "MessageRegisterResponse{" +
                "address=" + address +
                '}';
    }
}
