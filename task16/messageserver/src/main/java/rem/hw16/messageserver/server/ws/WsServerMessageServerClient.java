package rem.hw16.messageserver.server.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rem.hw16.messageserver.client.MessageServerClientImpl;
import rem.hw16.messageserver.core.Message;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class WsServerMessageServerClient extends MessageServerClientImpl {
    private static final Logger logger = Logger.getLogger(WsServerMessageServerClient.class.getName());

    @Autowired
    public WsServerMessageServerClient(String host, int port) {
        super(host, port, "WS", "DB");
    }

    @Override
    public void startClientLoop() {
        initClient();
        System.out.println("wsClientLoop");
        do {
            try {
                final Message message = getSocketClient().take();
                logger.log(Level.INFO, "Got message: " + message);
                /*
                if (message instanceof MessageGetUserByIdRequest) {
                    handleMessageGetUserByIdRequest((MessageGetUserByIdRequest) message);
                } else if (message instanceof MessageSaveUserRequest) {
                    handleMessageSaveUserRequest((MessageSaveUserRequest) message);
                } else {
                    logger.log(Level.WARNING, "Message type is unknown(" + message.getClass() + ")");
                }
                //*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
