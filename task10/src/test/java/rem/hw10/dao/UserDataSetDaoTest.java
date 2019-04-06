package rem.hw10.dao;

import org.junit.jupiter.api.*;
import rem.hw10.dbcommon.ConnectionHelper;
import rem.hw10.dbcommon.DBService;
import rem.hw10.dbcommon.DBServiceImpl;
import rem.hw10.domain.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserDataSetDaoTest {
    private static Connection connection;
    private static DBService dbService;
    private static DataSetDao dataSetDao;
    private static UserDataSet expected;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        connection = ConnectionHelper.getConnection();
        dbService = new DBServiceImpl(connection);
        dataSetDao = new UserDataSetDao(connection);
        expected = new UserDataSet(0L, "expected", 20);
    }

    @AfterAll
    public static void afterAll() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        dbService.createTables();
        dataSetDao.save(expected);
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        dbService.deleteTables();
    }

    @Test
    public void loadAdd() throws SQLException {
        final List<UserDataSet> actual = dataSetDao.loadAll(UserDataSet.class);
        assertTrue(actual.size() > 0);
        assertThat(actual, hasItems(expected));
    }

    @Test
    public void save() throws SQLException {
        final List<UserDataSet> listBefore = dataSetDao.loadAll(UserDataSet.class);
        final UserDataSet one = new UserDataSet("one", 21);
        dataSetDao.save(one);
        final List<UserDataSet> listAfter = dataSetDao.loadAll(UserDataSet.class);
        one.setId((long) (listAfter.size() - 1));
        assertEquals(listBefore.size() + 1, listAfter.size());
        assertThat(listAfter, hasItems(one));
    }

    @Test
    public void load() throws SQLException {
        final List<UserDataSet> list = dataSetDao.loadAll(UserDataSet.class);
        assertTrue(list.size() > 0);
        final UserDataSet userDataSet = list.get(0);
        final UserDataSet actual = dataSetDao.load(userDataSet.getId(), UserDataSet.class);
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}