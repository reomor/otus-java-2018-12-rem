package rem.hw10.dbcommon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    public static Connection getConnection() throws SQLException {
        String user = "SA";
        String password = "";
        String connectionString = "jdbc:hsqldb:mem:mymemdb";
        return DriverManager.getConnection(connectionString, user, password);
    }
}
