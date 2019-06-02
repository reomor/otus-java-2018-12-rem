package rem.hw16.messageServer.message;

import rem.hw16.messageServer.core.Message;

public class MessageRegisterRequest extends Message {
    private final String prefix;

    public MessageRegisterRequest(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return "MessageRegisterRequest{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
