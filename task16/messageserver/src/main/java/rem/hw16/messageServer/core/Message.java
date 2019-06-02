package rem.hw16.messageServer.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class Message implements Serializable {
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
}
