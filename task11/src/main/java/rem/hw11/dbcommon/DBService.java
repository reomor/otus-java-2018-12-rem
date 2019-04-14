package rem.hw11.dbcommon;

import rem.hw11.domain.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DBService<T extends DataSet> {
    String getConnectionMetaData() throws SQLException;

    void save(T dataSetEntity) throws SQLException;

    T load(long id) throws SQLException;

    List<T> loadAll() throws SQLException;

    void createTables() throws SQLException;

    void deleteTables() throws SQLException;

    void closeConnection();

    Class<T> getType();
}
