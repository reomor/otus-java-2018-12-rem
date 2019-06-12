package rem.hw16.messageserver.core;

public class WsMessage extends AddressedMessage {
    private final String content;

    public WsMessage(Address from, Address to, String content) {
        super(from, to);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "WsMessage{" +
                "from=" + super.getFrom() +
                ", to=" + super.getTo() +
                "content='" + content + '\'' +
                '}';
    }
}
