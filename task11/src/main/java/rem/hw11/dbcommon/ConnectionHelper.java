package rem.hw11.dbcommon;

import rem.hw11.exception.ConnectionException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.StringJoiner;

public class ConnectionHelper {
    public static String getConnectionMetadata(Connection connection) {
        final StringJoiner joiner = new StringJoiner("\n");
        try {
            joiner.add("Autocommit: " + connection.getAutoCommit());
            final DatabaseMetaData metaData = connection.getMetaData();
            joiner.add("DB name: " + metaData.getDatabaseProductName());
            joiner.add("DB version: " + metaData.getDatabaseProductVersion());
            joiner.add("Driver name: " + metaData.getDriverName());
            joiner.add("Driver version: " + metaData.getDriverVersion());
            joiner.add("JDBC version: " + metaData.getJDBCMajorVersion() + '.' + metaData.getJDBCMinorVersion());
        } catch (SQLException e) {
            throw new ConnectionException(e.getLocalizedMessage());
        }
        return joiner.toString();
    }
}
