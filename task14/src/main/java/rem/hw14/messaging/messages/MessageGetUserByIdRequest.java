package rem.hw14.messaging.messages;

import rem.hw14.dbcommon.DBService;
import rem.hw14.domain.DataSet;
import rem.hw14.domain.UserDataSet;
import rem.hw14.messaging.MessageToDB;
import rem.hw14.messaging.core.Address;

import java.util.logging.Level;

public class MessageGetUserByIdRequest extends MessageToDB {
    private final long id;

    public MessageGetUserByIdRequest(Address from, Address to, long id) {
        super(from, to);
        this.id = id;
    }

    @Override
    public <T extends DataSet> void exec(DBService<T> dbService) {
        logger.log(Level.INFO, "Get message request (id=" + id + ")");
        final T object = dbService.load(id);
        if (object instanceof UserDataSet) {
            dbService.getMS().sendMessage(new MessageGetUserByIdResponse<>(getTo(), getFrom(), (UserDataSet) object));
        } else {
            logger.log(Level.SEVERE, "Object type from DB(" + object.getClass() + ") is not supported");
        }
    }
}
