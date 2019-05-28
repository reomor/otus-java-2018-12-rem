package rem.hw15.messaging.messages;

import rem.hw15.dbcommon.DBService;
import rem.hw15.domain.DataSet;
import rem.hw15.domain.UserDataSet;
import rem.hw15.messaging.MessageToDB;
import rem.hw15.messaging.core.Address;

import java.util.logging.Level;

public class MessageSaveUserRequest extends MessageToDB {
    private final UserDataSet userDataSet;

    public MessageSaveUserRequest(Address from, Address to, UserDataSet userDataSet) {
        super(from, to);
        this.userDataSet = userDataSet;
    }

    @Override
    public <T extends DataSet> void exec(DBService<T> dbService) {
        logger.log(Level.INFO, "Save object(" + userDataSet.getClass() + ")");
        dbService.save((T) userDataSet);
    }
}
