package rem.hw10.dbcommon;

import java.sql.SQLException;

public interface DDLService {
    String getConnectionMetaData() throws SQLException;

    void createTables() throws SQLException;

    void deleteTables() throws SQLException;
}
