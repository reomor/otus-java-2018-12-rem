package rem.hw10.dbcommon;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.StringJoiner;

public class DBServiceImpl implements DBService {
    private final Connection connection;

    public DBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getConnectionMetaData() throws SQLException {
        final StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Autocommit: " + connection.getAutoCommit());
        final DatabaseMetaData metaData = connection.getMetaData();
        joiner.add("DB name: " + metaData.getDatabaseProductName());
        joiner.add("DB version: " + metaData.getDatabaseProductVersion());
        joiner.add("Driver name: " + metaData.getDriverName());
        joiner.add("Driver version: " + metaData.getDriverVersion());
        joiner.add("JDBC version: " + metaData.getJDBCMajorVersion() + '.' + metaData.getJDBCMinorVersion());
        return joiner.toString();
    }

    @Override
    public void createTables() throws SQLException {

    }

    @Override
    public void deleteTables() throws SQLException {

    }

}
