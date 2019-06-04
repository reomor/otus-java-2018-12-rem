package rem.hw16.frontend.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rem.hw16.dbserver.domain.UserDataSet;
import rem.hw16.frontend.client.FrontendMessageServerClient;
import rem.hw16.messageserver.core.Message;
import rem.hw16.messageserver.message.MessageGetUserByIdRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FrontServiceImpl implements FrontService {
    private final Map<Long, UserDataSet> userDataSetMap = new HashMap<>();
    private final FrontendMessageServerClient frontendMessageServerClient;

    @Autowired
    public FrontServiceImpl(FrontendMessageServerClient frontendMessageServerClient) {
        this.frontendMessageServerClient = frontendMessageServerClient;
    }

    @Override
    public void requestById(long id) {
        Message message = new MessageGetUserByIdRequest(
                frontendMessageServerClient.getAddressFrom(),
                frontendMessageServerClient.getAddressTo(),
                id);
        frontendMessageServerClient.getSocketClient().send(message);
    }

    @Override
    public <T extends UserDataSet> void requestToSave(T userData) {
//        Message message = new MessageSaveUserRequest(getAddress(), channel.getBackEnd(), userData);
//        channel.getMessageSystem().sendMessage(message);
    }

    @Override
    public <T extends UserDataSet> void addUserData(T userData) {
        userDataSetMap.put(userData.getId(), userData);
    }

    @Override
    public <T extends UserDataSet> T getUserData(long id) {
        if (userDataSetMap.containsKey(id)) {
            return (T) userDataSetMap.get(id);
        }
        requestById(id);
        return null;
    }

    @Override
    public Collection<UserDataSet> getUserDataSetCollection() {
        return Collections.unmodifiableCollection(userDataSetMap.values());
    }
}
