package rem.hw14.messaging;

import rem.hw14.dbcommon.DBService;
import rem.hw14.domain.DataSet;
import rem.hw14.messaging.core.Address;
import rem.hw14.messaging.core.Addressee;
import rem.hw14.messaging.core.Message;

public abstract class MessageToDB extends Message {
    public MessageToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService<DataSet>) addressee);
        }
    }

    public abstract <T extends DataSet> void exec(DBService<T> dbService);
}
