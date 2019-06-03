package rem.hw16.messageserver.message;

import rem.hw16.messageserver.core.Address;

public class MessageJsonWithClass extends MessageJson {
    private final String clazz;

    public MessageJsonWithClass(Address from, Address to, String jsonMessage, Class<?> clazz) {
        super(from, to, jsonMessage);
        this.clazz = clazz.getName();
    }

    public String getClazz() {
        return clazz;
    }
}
