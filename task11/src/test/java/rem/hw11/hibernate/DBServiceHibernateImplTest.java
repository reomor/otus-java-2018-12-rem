package rem.hw11.hibernate;

import org.hamcrest.MatcherAssert;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import rem.hw11.dao.DataSetDao;
import rem.hw11.dbcommon.DBService;
import rem.hw11.dbcommon.DDLService;
import rem.hw11.domain.AddressDataSet;
import rem.hw11.domain.PhoneDataSet;
import rem.hw11.domain.UserDataSet;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DBServiceHibernateImpl must")
class DBServiceHibernateImplTest {
    private DataSetDao<UserDataSet> dataSetDao;
    private DBService<UserDataSet> dbService;
    private DDLService ddlService;

    private static UserDataSet expected;

    @BeforeEach
    public void setUp() {
        AddressDataSet addressDataSet = new AddressDataSet("Lenina, 1");
        expected = new UserDataSet("expected", 20, addressDataSet);
        expected.setPhones(new PhoneDataSet("1234567890"), new PhoneDataSet("0987654321"));

        final Configuration configuration = HibernateHelper.getConfigurationWithAnnotatedClasses();
        final SessionFactory sessionFactory = HibernateHelper.getSessionFactory(configuration);
        ddlService = new DDLServiceImpl(sessionFactory);
        dataSetDao = new UserDataSetHibernateDaoImpl(sessionFactory);
        dbService = new DBServiceHibernateImpl(sessionFactory, dataSetDao);
        dbService.save(expected);
    }

    @AfterEach
    public void setDown() {
        ddlService.deleteTables();
    }

    @Test
    @DisplayName("load all records from table UserDataSet")
    public void should_ReturnNotEmptyListOfRecordsWithKnownOne_WhenLoadAll() {
        final List<UserDataSet> actual = dbService.loadAll();
        assertTrue(actual.size() > 0);
        MatcherAssert.assertThat(actual, hasItems(expected));
    }

    @Test
    @DisplayName("save one UserDataSet entity")
    public void should_ReturnListWithNewUserDataSet_WhenSaveOne() {
        final List<UserDataSet> listBefore = dbService.loadAll();
        final UserDataSet one = new UserDataSet("one", 21, new AddressDataSet("Lenina, 1"));
        dbService.save(one);
        final List<UserDataSet> listAfter = dbService.loadAll();
        one.setId((long) (listAfter.size() - 1));
        assertEquals(listBefore.size() + 1, listAfter.size());
        MatcherAssert.assertThat(listAfter, hasItems(one));
    }

    @Test
    @DisplayName("load one UserDataSet entity by id")
    public void should_ReturnOneUserDataSet_WhenLoadById() {
        final List<UserDataSet> list = dbService.loadAll();
        assertTrue(list.size() > 0);
        final UserDataSet userDataSet = list.get(0);
        final UserDataSet actual = (UserDataSet) dbService.load(userDataSet.getId());
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id", "name", "age");
        assertThat(actual.getAddress()).isEqualToComparingOnlyGivenFields(expected.getAddress(), "id", "street");
    }
}