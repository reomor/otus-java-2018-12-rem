package rem.hw14.messaging.messages;

import rem.hw14.domain.UserDataSet;
import rem.hw14.front.FrontService;
import rem.hw14.messaging.MessageToFront;
import rem.hw14.messaging.core.Address;

import java.util.logging.Level;

public class MessageGetUserByIdResponse<T extends UserDataSet> extends MessageToFront {
    private final T payload;

    public MessageGetUserByIdResponse(Address from, Address to, T payload) {
        super(from, to);
        this.payload = payload;
    }

    @Override
    public void exec(FrontService frontService) {
        logger.log(Level.INFO, "Get message response (payload=" + payload + ")");
        frontService.addUserData(payload);
    }
}
