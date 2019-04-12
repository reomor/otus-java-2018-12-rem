package rem.hw11.dbcommon;

import java.sql.SQLException;

public interface DBService {
    String getConnectionMetaData() throws SQLException;

    void createTables() throws SQLException;

    void deleteTables() throws SQLException;
}
