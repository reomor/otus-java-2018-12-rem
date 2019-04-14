package rem.hw11;

import org.junit.jupiter.api.AfterAll;
import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.AddressDataSet;
import rem.hw11.domain.UserDataSet;

public abstract class AbstractDBServiceTest {
    protected static DBService dbService;
    protected static UserDataSet expected;

    public void setUp() {
        AddressDataSet addressDataSet = new AddressDataSet("Lenina, 1");
        expected = new UserDataSet("expected", 20, addressDataSet);
    }

    @AfterAll
    public static void afterAll() {
        dbService.closeConnection();
    }
}
