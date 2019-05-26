package rem.hw14.front;

import rem.hw14.domain.UserDataSet;
import rem.hw14.messaging.MessageChannel;
import rem.hw14.messaging.core.Address;
import rem.hw14.messaging.core.Message;
import rem.hw14.messaging.core.MessageSystem;
import rem.hw14.messaging.messages.MessageGetUserByIdRequest;

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
    public void handleRequest(long id) {
        Message message = new MessageGetUserByIdRequest(getAddress(), channel.getBackEnd(), id);
        channel.getMessageSystem().sendMessage(message);
    }

    @Override
    public <T extends UserDataSet> void addUserData(T userData) {
        userDataSetMap.put(userData.getId(), userData);
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
