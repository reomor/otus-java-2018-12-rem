package rem.hw14.front;

import rem.hw14.domain.UserDataSet;
import rem.hw14.messaging.core.Addressee;

import java.util.Collection;

public interface FrontService extends Addressee {
    void handleRequest(long id);

    <T extends UserDataSet> void addUserData(T userData);

    Collection<UserDataSet> getUserDataSetCollection();
}
