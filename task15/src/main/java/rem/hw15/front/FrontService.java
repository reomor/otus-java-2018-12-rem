package rem.hw15.front;

import rem.hw15.domain.UserDataSet;
import rem.hw15.messaging.core.Addressee;

import java.util.Collection;

public interface FrontService extends Addressee {
    void handleRequest(long id);

    <T extends UserDataSet> void addUserData(T userData);

    Collection<UserDataSet> getUserDataSetCollection();
}
