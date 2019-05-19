package rem.hw15;

import rem.hw15.dbcommon.DBService;
import rem.hw15.domain.AddressDataSet;
import rem.hw15.domain.UserDataSet;
import rem.hw15.front.FrontService;
import rem.hw15.front.FrontServiceImpl;
import rem.hw15.hibernate.DBServiceHibernateImpl;
import rem.hw15.messaging.MessageChannel;
import rem.hw15.messaging.core.Address;
import rem.hw15.messaging.core.MessageSystem;
import rem.hw15.web.WebServer;

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
