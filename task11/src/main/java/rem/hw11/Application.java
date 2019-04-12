package rem.hw11;

import rem.hw11.dao.DataSetDao;
import rem.hw11.dao.UserDataSetMyOrmDao;
import rem.hw11.dbcommon.ConnectionHelper;
import rem.hw11.dbcommon.DBService;
import rem.hw11.dbcommon.DBServiceMyOrmImpl;
import rem.hw11.domain.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try(final Connection connection = ConnectionHelper.getConnection()) {
            DBService DBService = new DBServiceMyOrmImpl(connection);
            System.out.println(connection.getSchema());
            DBService.createTables();
            System.out.println(DBService.getConnectionMetaData());
            DataSetDao dataSetDao = new UserDataSetMyOrmDao(connection);
            dataSetDao.save(new UserDataSet("name", 12));
            final UserDataSet load = dataSetDao.load(0, UserDataSet.class);
            System.out.println(load);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
