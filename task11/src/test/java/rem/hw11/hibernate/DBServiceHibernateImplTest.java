package rem.hw11.hibernate;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.UserDataSet;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DBServiceHibernateImpl must")
class DBServiceHibernateImplTest {
    private static DBService dbService;
    private static UserDataSet expected;

    @BeforeAll
    public static void beforeAll() {
        dbService = new DBServiceHibernateImpl();
        expected = new UserDataSet(0L, "expected", 20);
    }

    @BeforeEach
    public void setUp() throws SQLException {
        dbService.save(expected);
    }

    @Test
    @DisplayName("load all records from table UserDataSet")
    public void should_ReturnNotEmptyListOfRecordsWithKnownOne_WhenLoadAll() throws SQLException {
        final List<UserDataSet> actual = dbService.loadAll();
        assertTrue(actual.size() > 0);
        MatcherAssert.assertThat(actual, hasItems(expected));
    }
}