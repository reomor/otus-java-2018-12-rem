package rem.hw11.myorm;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import rem.hw11.AbstractDBServiceTest;
import rem.hw11.dbcommon.ConnectionHelper;
import rem.hw11.domain.AddressDataSet;
import rem.hw11.domain.UserDataSet;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DBServiceMyOrmImpl must")
class DBServiceMyOrmImplTest extends AbstractDBServiceTest {
    @BeforeAll
    public static void beforeAll() {
        try {
            dbService = new DBServiceMyOrmImpl(ConnectionHelper.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        try {
            dbService.createTables();
            dbService.save(expected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("load all records from table UserDataSet")
    public void should_ReturnNotEmptyListOfRecordsWithKnownOne_WhenLoadAll() throws SQLException {
        final List<UserDataSet> actual = dbService.loadAll();
        assertTrue(actual.size() > 0);
        MatcherAssert.assertThat(actual, hasItems(expected));
    }

    @Test
    @DisplayName("save one UserDataSet entity")
    public void should_ReturnListWithNewUserDataSet_WhenSaveOne() throws SQLException {
        final List<UserDataSet> listBefore = dbService.loadAll();
        AddressDataSet addressDataSet = new AddressDataSet(0L,"Lenina, 1");
        final UserDataSet one = new UserDataSet("one", 21, addressDataSet);
        dbService.save(one);
        final List<UserDataSet> listAfter = dbService.loadAll();
        one.setId((long) (listAfter.size() - 1));
        assertEquals(listBefore.size() + 1, listAfter.size());
        MatcherAssert.assertThat(listAfter, hasItems(one));
    }

    @Test
    @DisplayName("load one UserDataSet entity by id")
    public void should_ReturnOneUserDataSet_WhenLoadById() throws SQLException {
        final List<UserDataSet> list = dbService.loadAll();
        assertTrue(list.size() > 0);
        final UserDataSet userDataSet = list.get(0);
        final UserDataSet actual = (UserDataSet) dbService.load(userDataSet.getId());
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}