package rem.hw11;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import rem.hw11.dao.DataSetDao;
import rem.hw11.dbcommon.DBService;
import rem.hw11.dbcommon.DDLService;
import rem.hw11.domain.AddressDataSet;
import rem.hw11.domain.DataSet;
import rem.hw11.domain.UserDataSet;
import rem.hw11.hibernate.DBServiceHibernateImpl;
import rem.hw11.hibernate.DDLServiceImpl;
import rem.hw11.hibernate.HibernateHelper;
import rem.hw11.hibernate.UserDataSetHibernateDaoImpl;

public class Application {
    public static void main(String[] args) {
        final Configuration configuration = HibernateHelper.getConfigurationWithAnnotatedClasses();
        final SessionFactory sessionFactory = HibernateHelper.getSessionFactory(configuration);
        DDLService ddlService = new DDLServiceImpl(sessionFactory);
        DataSetDao<UserDataSet> dataSetDao = new UserDataSetHibernateDaoImpl(sessionFactory);
        DBService dbServiceHibernate = new DBServiceHibernateImpl(sessionFactory, dataSetDao);
        System.out.println(dbServiceHibernate.getConnectionMetaData());

        dbServiceHibernate.save(new UserDataSet("name", 12, new AddressDataSet("Lenina, 1")));
        final DataSet dataSet = dbServiceHibernate.load(1);
        if (dataSet != null) {
            System.out.println(dataSet);
        }
    }
}
