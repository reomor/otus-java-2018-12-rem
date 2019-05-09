package rem.hw14.messaging.core;

import java.util.logging.Logger;

public abstract class Message {
    protected final static Logger logger = Logger.getLogger(Message.class.getName());
    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
}
