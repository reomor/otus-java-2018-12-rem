package rem.hw16.messageserver.message;

import rem.hw16.messageserver.core.Address;

public class MessageSaveUserRequest extends MessageJsonWithClass{
    public MessageSaveUserRequest(Address from, Address to, String jsonMessage, Class<?> clazz) {
        super(from, to, jsonMessage, clazz);
    }
}
