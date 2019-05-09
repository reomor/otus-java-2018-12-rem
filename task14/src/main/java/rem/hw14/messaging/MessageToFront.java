package rem.hw14.messaging;

import rem.hw14.front.FrontService;
import rem.hw14.messaging.core.Address;
import rem.hw14.messaging.core.Addressee;
import rem.hw14.messaging.core.Message;

import java.util.logging.Level;

public abstract class MessageToFront extends Message {
    public MessageToFront(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontService) {
            exec((FrontService) addressee);
        } else {
            logger.log(Level.SEVERE, "Addressee type (type=" + addressee.getClass() + ", address=" + addressee.getAddress() + ") don't correspond message type");
        }
    }

    public abstract void exec(FrontService frontService);
}
