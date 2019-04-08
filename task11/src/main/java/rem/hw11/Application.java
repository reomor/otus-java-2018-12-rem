package rem.hw11;

import rem.hw11.dao.DataSetDao;
import rem.hw11.dao.UserDataSetDao;
import rem.hw11.dbcommon.ConnectionHelper;
import rem.hw11.dbcommon.DDLService;
import rem.hw11.dbcommon.DDLServiceImpl;
import rem.hw11.domain.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try(final Connection connection = ConnectionHelper.getConnection()) {
            DDLService DDLService = new DDLServiceImpl(connection);
            System.out.println(connection.getSchema());
            DDLService.createTables();
            System.out.println(DDLService.getConnectionMetaData());
            DataSetDao dataSetDao = new UserDataSetDao(connection);
            dataSetDao.save(new UserDataSet("name", 12));
            final UserDataSet load = dataSetDao.load(0, UserDataSet.class);
            System.out.println(load);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
