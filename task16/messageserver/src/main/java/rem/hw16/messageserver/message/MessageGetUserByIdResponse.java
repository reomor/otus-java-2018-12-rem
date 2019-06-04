package rem.hw16.messageserver.message;

import rem.hw16.messageserver.core.Address;

public class MessageGetUserByIdResponse extends MessageJsonWithClass {
    public MessageGetUserByIdResponse(Address from, Address to, String jsonMessage, Class<?> clazz) {
        super(from, to, jsonMessage, clazz);
    }
}
