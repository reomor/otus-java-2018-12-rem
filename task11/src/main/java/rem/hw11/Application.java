package rem.hw11;

import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.AddressDataSet;
import rem.hw11.domain.DataSet;
import rem.hw11.domain.UserDataSet;
import rem.hw11.hibernate.DBServiceHibernateImpl;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try {
            DBService dbServiceHibernate = new DBServiceHibernateImpl();
            System.out.println(dbServiceHibernate.getConnectionMetaData());
            dbServiceHibernate.save(new UserDataSet("name", 12, new AddressDataSet("Lenina, 1")));
            final DataSet load = dbServiceHibernate.load(1);
            System.out.println(load);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
