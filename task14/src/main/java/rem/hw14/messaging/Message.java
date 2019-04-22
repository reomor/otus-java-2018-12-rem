package rem.hw14.messaging;

public abstract class Message {
    private final Address from;
    private final Address to;

    protected Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }
}
