package rem.hw15.messaging;

import rem.hw15.dbcommon.DBService;
import rem.hw15.domain.DataSet;
import rem.hw15.messaging.core.Address;
import rem.hw15.messaging.core.Addressee;
import rem.hw15.messaging.core.Message;

import java.util.logging.Level;

public abstract class MessageToDB extends Message {
    public MessageToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService<DataSet>) addressee);
        } else {
            logger.log(Level.SEVERE, "Addressee type (type=" + addressee.getClass() + ", " +
                    "address=" + addressee.getAddress() + ") don't correspond message type");
        }
    }

    public abstract <T extends DataSet> void exec(DBService<T> dbService);
}
