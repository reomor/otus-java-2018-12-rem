package rem.hw11;

import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.AddressDataSet;
import rem.hw11.domain.DataSet;
import rem.hw11.domain.UserDataSet;
import rem.hw11.hibernate.DBServiceHibernateImpl;


public class Application {
    public static void main(String[] args) {
        DBService dbServiceHibernate = new DBServiceHibernateImpl();
        System.out.println(dbServiceHibernate.getConnectionMetaData());
        dbServiceHibernate.save(new UserDataSet("name", 12, new AddressDataSet("Lenina, 1")));
        final DataSet dataSet = dbServiceHibernate.load(1);
        if (dataSet != null) {
            System.out.println(dataSet);
        }
    }
}
