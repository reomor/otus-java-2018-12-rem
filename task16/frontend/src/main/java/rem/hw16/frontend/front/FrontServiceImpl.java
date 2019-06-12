package rem.hw16.frontend.front;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import rem.hw16.dbserver.domain.UserDataSet;
import rem.hw16.frontend.client.FrontendMessageServerClient;
import rem.hw16.messageserver.core.Message;
import rem.hw16.messageserver.message.MessageGetUserByIdRequest;
import rem.hw16.messageserver.message.MessageSaveUserRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FrontServiceImpl implements FrontService {
    private final Map<Long, UserDataSet> userDataSetMap = new HashMap<>();
    private static final Gson gson = new GsonBuilder().create();

    private FrontendMessageServerClient frontendMessageServerClient;

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
        Message message = new MessageSaveUserRequest(
                frontendMessageServerClient.getAddressFrom(),
                frontendMessageServerClient.getAddressTo(),
                gson.toJson(userData, userData.getClass()),
                userData.getClass());
        frontendMessageServerClient.getSocketClient().send(message);
    }

    @Override
    public <T extends UserDataSet> void addUserData(T userData) {
        userDataSetMap.put(userData.getId(), userData);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends UserDataSet> T getUserData(long id) {
        if (userDataSetMap.containsKey(id)) {
            // cast is safe because of extends
            return (T) userDataSetMap.get(id);
        }
        requestById(id);
        return null;
    }

    @Override
    public Collection<UserDataSet> getUserDataSetCollection() {
        return Collections.unmodifiableCollection(userDataSetMap.values());
    }

    public void setFrontendMessageServerClient(FrontendMessageServerClient frontendMessageServerClient) {
        this.frontendMessageServerClient = frontendMessageServerClient;
    }
}
