package rem.hw14.messaging;

import rem.hw14.messaging.core.Addressee;
import rem.hw14.messaging.core.MessageSystem;

public class MessageChannel {
    private final MessageSystem messageSystem;
    private final Addressee frontEnd;
    private final Addressee backEnd;

    public MessageChannel(MessageSystem messageSystem, Addressee frontEnd, Addressee backEnd) {
        this.messageSystem = messageSystem;
        this.frontEnd = frontEnd;
        this.backEnd = backEnd;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Addressee getFrontEnd() {
        return frontEnd;
    }

    public Addressee getBackEnd() {
        return backEnd;
    }
}
