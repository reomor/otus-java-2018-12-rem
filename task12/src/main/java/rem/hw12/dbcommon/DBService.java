package rem.hw12.dbcommon;

import rem.hw12.domain.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DBService<T extends DataSet> {
    String getConnectionMetaData() throws SQLException ;

    void save(T dataSetEntity);

    T load(long id);

    List<T> loadAll();

    void deleteTables();

    void closeConnection();

    Class<T> getType();
}
