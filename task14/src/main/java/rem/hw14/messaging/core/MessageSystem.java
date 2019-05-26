package rem.hw14.messaging.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());

    private final List<Thread> workers;
    private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;
    private final Map<Address, Addressee> addresseeMap;

    public MessageSystem() {
        workers = new ArrayList<>();
        messagesMap = new HashMap<>();
        addresseeMap = new HashMap<>();
    }

    public void registerAddressee(Addressee addressee) {
        final Address address = addressee.getAddress();
        addresseeMap.put(address, addressee);
        messagesMap.put(address, new LinkedBlockingQueue<>());
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }

    public void start() {
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
            String name = "MS-worker-" + entry.getKey().getId();
            Thread thread = new Thread(() -> {
                final LinkedBlockingQueue<Message> queue = messagesMap.get(entry.getKey());
                while (true) {
                    try {
                        final Message message = queue.take();
                        message.exec(entry.getValue());
                    } catch (InterruptedException e) {
                        logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
                        return;
                    }
                }
            });
            thread.setName(name);
            workers.add(thread);
            thread.start();
        }
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
    }
}
