package rem.hw15.front;

import rem.hw15.domain.UserDataSet;
import rem.hw15.messaging.MessageChannel;
import rem.hw15.messaging.core.Address;
import rem.hw15.messaging.core.Message;
import rem.hw15.messaging.core.MessageSystem;
import rem.hw15.messaging.messages.MessageGetUserByIdRequest;
import rem.hw15.messaging.messages.MessageSaveUserRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FrontServiceImpl implements FrontService {
    private final MessageChannel channel;
    private final Address address;
    private final Map<Long, UserDataSet> userDataSetMap = new HashMap<>();

    public FrontServiceImpl(MessageChannel channel, Address address) {
        this.channel = channel;
        this.address = address;
        channel.setFrontEnd(address);
        channel.getMessageSystem().registerAddressee(this);
    }

    @Override
    public void requestById(long id) {
        Message message = new MessageGetUserByIdRequest(getAddress(), channel.getBackEnd(), id);
        channel.getMessageSystem().sendMessage(message);
    }

    @Override
    public <T extends UserDataSet> void requestToSave(T userData) {
        Message message = new MessageSaveUserRequest(getAddress(), channel.getBackEnd(), userData);
        channel.getMessageSystem().sendMessage(message);
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

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return channel.getMessageSystem();
    }
}
