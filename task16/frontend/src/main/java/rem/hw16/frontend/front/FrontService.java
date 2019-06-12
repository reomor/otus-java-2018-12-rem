package rem.hw16.frontend.front;

import rem.hw16.dbserver.domain.UserDataSet;

import java.util.Collection;

public interface FrontService {
    void requestById(long id);

    <T extends UserDataSet> void requestToSave(T userData);

    <T extends UserDataSet> void addUserData(T userData);

    <T extends UserDataSet> T getUserData(long id);

    Collection<UserDataSet> getUserDataSetCollection();
}
