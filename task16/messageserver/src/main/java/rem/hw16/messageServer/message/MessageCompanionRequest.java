package rem.hw16.messageServer.message;

import rem.hw16.messageServer.core.Message;

public class MessageCompanionRequest extends Message {
    private final String prefix;

    public MessageCompanionRequest(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return "MessageCompanionRequest{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
