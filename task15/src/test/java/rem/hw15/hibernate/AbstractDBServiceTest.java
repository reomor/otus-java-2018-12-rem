package rem.hw15.hibernate;

import org.junit.jupiter.api.AfterAll;
import rem.hw15.dbcommon.DBService;
import rem.hw15.domain.AddressDataSet;
import rem.hw15.domain.PhoneDataSet;
import rem.hw15.domain.UserDataSet;
import rem.hw15.messaging.MessageChannel;
import rem.hw15.messaging.core.MessageSystem;

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
