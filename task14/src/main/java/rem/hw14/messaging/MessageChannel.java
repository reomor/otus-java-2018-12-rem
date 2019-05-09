package rem.hw14.messaging;

import rem.hw14.messaging.core.Address;
import rem.hw14.messaging.core.MessageSystem;

public class MessageChannel {
    private final MessageSystem messageSystem;
    private Address frontEnd;
    private Address backEnd;

    public MessageChannel(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontEnd() {
        return frontEnd;
    }

    public void setFrontEnd(Address frontEnd) {
        this.frontEnd = frontEnd;
    }

    public Address getBackEnd() {
        return backEnd;
    }

    public void setBackEnd(Address backEnd) {
        this.backEnd = backEnd;
    }
}
