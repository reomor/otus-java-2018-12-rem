package rem.hw10;

import rem.hw10.dao.DataSetDao;
import rem.hw10.dao.UserDataSetDao;
import rem.hw10.dbcommon.ConnectionHelper;
import rem.hw10.dbcommon.DBService;
import rem.hw10.dbcommon.DBServiceImpl;
import rem.hw10.domain.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try(final Connection connection = ConnectionHelper.getConnection()) {
            DBService dbService = new DBServiceImpl(connection);
            System.out.println(connection.getSchema());
            dbService.createTables();
            System.out.println(dbService.getConnectionMetaData());
            DataSetDao dataSetDao = new UserDataSetDao(connection);
            dataSetDao.save(new UserDataSet("name", 12));
            final UserDataSet load = dataSetDao.load(0, UserDataSet.class);
            System.out.println(load);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
