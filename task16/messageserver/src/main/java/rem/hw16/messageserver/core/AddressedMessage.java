package rem.hw16.messageserver.core;

public abstract class AddressedMessage extends Message {
    private final Address from;
    private final Address to;

    public AddressedMessage(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "AddressedMessage{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
