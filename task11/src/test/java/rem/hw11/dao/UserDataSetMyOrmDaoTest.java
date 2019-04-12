package rem.hw11.dao;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import rem.hw11.dbcommon.ConnectionHelper;
import rem.hw11.dbcommon.DBService;
import rem.hw11.myorm.DBServiceMyOrmImpl;
import rem.hw11.domain.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserDataSetMyOrmDao must")
class UserDataSetMyOrmDaoTest {
    private static Connection connection;
    private static DBService dbService;
    private static UserDataSet expected;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        connection = ConnectionHelper.getConnection();
        dbService = new DBServiceMyOrmImpl(connection);
        expected = new UserDataSet(0L, "expected", 20);
    }

    @AfterAll
    public static void afterAll() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        dbService.createTables();
        dbService.save(expected);
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        dbService.deleteTables();
    }

    @Test
    @DisplayName("load all records from table UserDataSet")
    public void should_ReturnNotEmptyListOfRecordsWithKnownOne_WhenLoadAll() throws SQLException {
        final List<UserDataSet> actual = dbService.loadAll(UserDataSet.class);
        assertTrue(actual.size() > 0);
        MatcherAssert.assertThat(actual, hasItems(expected));
    }

    @Test
    @DisplayName("save one UserDataSet entity")
    public void should_ReturnListWithNewUserDataSet_WhenSaveOne() throws SQLException {
        final List<UserDataSet> listBefore = dbService.loadAll(UserDataSet.class);
        final UserDataSet one = new UserDataSet("one", 21);
        dbService.save(one);
        final List<UserDataSet> listAfter = dbService.loadAll(UserDataSet.class);
        one.setId((long) (listAfter.size() - 1));
        assertEquals(listBefore.size() + 1, listAfter.size());
        MatcherAssert.assertThat(listAfter, hasItems(one));
    }

    @Test
    @DisplayName("load one UserDataSet entity by id")
    public void should_ReturnOneUserDataSet_WhenLoadById() throws SQLException {
        final List<UserDataSet> list = dbService.loadAll(UserDataSet.class);
        assertTrue(list.size() > 0);
        final UserDataSet userDataSet = list.get(0);
        final UserDataSet actual = dbService.load(userDataSet.getId(), UserDataSet.class);
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}