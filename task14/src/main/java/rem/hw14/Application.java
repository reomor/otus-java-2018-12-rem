package rem.hw14;

import rem.hw14.dbcommon.DBService;
import rem.hw14.domain.AddressDataSet;
import rem.hw14.domain.UserDataSet;
import rem.hw14.front.FrontService;
import rem.hw14.front.FrontServiceImpl;
import rem.hw14.hibernate.DBServiceHibernateImpl;
import rem.hw14.messaging.MessageChannel;
import rem.hw14.messaging.core.Address;
import rem.hw14.messaging.core.MessageSystem;
import rem.hw14.web.WebServer;

public class Application {
    public static void main(String[] args) {
        MessageSystem messageSystem = new MessageSystem();
        MessageChannel messageChannel = new MessageChannel(messageSystem);
        final DBService<UserDataSet> dbService = new DBServiceHibernateImpl(messageChannel, new Address("DB"));
        final FrontService frontService = new FrontServiceImpl(messageChannel, new Address("FRONT"));
        messageSystem.start();

        dbService.save(new UserDataSet("First", 1, new AddressDataSet("Lenina, 1")));

        WebServer server = new WebServer(dbService, frontService);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
