package rem.hw11;

import org.junit.jupiter.api.AfterAll;
import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.AddressDataSet;
import rem.hw11.domain.PhoneDataSet;
import rem.hw11.domain.UserDataSet;

public abstract class AbstractDBServiceTest {
    protected static DBService dbService;
    protected static UserDataSet expected;

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
