package rem.hw16.dbserver.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rem.hw16.dbserver.dbcommon.DBService;
import rem.hw16.dbserver.domain.UserDataSet;
import rem.hw16.messageserver.client.MessageServerClientImpl;
import rem.hw16.messageserver.core.Message;
import rem.hw16.messageserver.message.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServiceMessageServerClient extends MessageServerClientImpl {
    private static final Logger logger = Logger.getLogger(DBServiceMessageServerClient.class.getName());
    public static final Gson gson = new GsonBuilder().create();
    private final DBService<UserDataSet> dbService;

    public DBServiceMessageServerClient(String host, int port, DBService<UserDataSet> dbService) {
        super(host, port, "DB", "FRONT");
        this.dbService = dbService;
        startClientLoop();
    }

    @Override
    public void startClientLoop() {
        initClient();
        do {
            try {
                final Message message = getSocketClient().take();
                if (message instanceof MessageGetUserByIdRequest) {
                    handleMessageGetUserByIdRequest((MessageGetUserByIdRequest) message);
                } else if (message instanceof MessageSaveUserRequest) {
                    handleMessageSaveUserRequest((MessageSaveUserRequest) message);
                } else {
                    logger.log(Level.WARNING, "Message type is unknown(" + message.getClass() + ")");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    private void handleMessageGetUserByIdRequest(MessageGetUserByIdRequest messageGetUserByIdRequest) {
        logger.log(Level.INFO, "Got message (" + messageGetUserByIdRequest.getClass() + ")");
        UserDataSet userDataSet = null;
        try {
            userDataSet = dbService.load(messageGetUserByIdRequest.getId());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error in DB during load object by id(" + messageGetUserByIdRequest.getId() + ")");
        }
        if (userDataSet != null) {
            getSocketClient().send(new MessageGetUserByIdResponse(
                    messageGetUserByIdRequest.getTo(),
                    messageGetUserByIdRequest.getFrom(),
                    gson.toJson(userDataSet, userDataSet.getClass()),
                    userDataSet.getClass())
            );
        } else {
            logger.log(Level.INFO, "Object by id(" + messageGetUserByIdRequest.getId() + ") is null");
        }
    }

    private void handleMessageSaveUserRequest(MessageSaveUserRequest messageSaveUserRequest) {
        logger.log(Level.INFO, "Got message (" + messageSaveUserRequest.getClass() + ")");
        try {
            final UserDataSet userDataSet = (UserDataSet) gson.fromJson(
                    messageSaveUserRequest.getJsonMessage(),
                    Class.forName(messageSaveUserRequest.getClazz())
            );
            logger.log(Level.INFO, "Save object(" + userDataSet + ") in DB");
            try {
                dbService.save(userDataSet);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error in DB during save object(" + userDataSet + ")");
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Object in message(" + messageSaveUserRequest + ") " +
                    "has wrong type(" + messageSaveUserRequest.getClazz() + ")");
        }
    }
}
