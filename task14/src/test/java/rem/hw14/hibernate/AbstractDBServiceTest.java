package rem.hw14.hibernate;

import org.junit.jupiter.api.AfterAll;
import rem.hw14.dbcommon.DBService;
import rem.hw14.domain.AddressDataSet;
import rem.hw14.domain.PhoneDataSet;
import rem.hw14.domain.UserDataSet;
import rem.hw14.messaging.MessageChannel;
import rem.hw14.messaging.core.MessageSystem;

public abstract class AbstractDBServiceTest {
    protected static DBService dbService;
    protected static UserDataSet expected;
    protected static MessageSystem messageSystem;
    protected static MessageChannel messageChannel;

    public void setUp() {
        AddressDataSet addressDataSet = new AddressDataSet("Lenina, 1");
        expected = new UserDataSet("expected", 20, addressDataSet);
        expected.setPhones(new PhoneDataSet("1234567890"), new PhoneDataSet("0987654321"));
    }

    @AfterAll
    public static void afterAll() {
        dbService.closeConnection();
    }
}
