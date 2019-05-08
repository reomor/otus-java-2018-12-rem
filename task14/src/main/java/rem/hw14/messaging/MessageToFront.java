package rem.hw14.messaging;

import rem.hw14.front.FrontService;
import rem.hw14.messaging.core.Address;
import rem.hw14.messaging.core.Addressee;
import rem.hw14.messaging.core.Message;

public abstract class MessageToFront extends Message {
    public MessageToFront(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontService) {
            exec((FrontService) addressee);
        }
    }

    public abstract void exec(FrontService frontService);
}
