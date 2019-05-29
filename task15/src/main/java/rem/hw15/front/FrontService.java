package rem.hw15.front;

import rem.hw15.domain.UserDataSet;
import rem.hw15.messaging.core.Addressee;

import java.util.Collection;

public interface FrontService extends Addressee {
    void requestById(long id);

    <T extends UserDataSet> void requestToSave(T userData);

    <T extends UserDataSet> void addUserData(T userData);

    <T extends UserDataSet> T getUserData(long id);

    Collection<UserDataSet> getUserDataSetCollection();
}
