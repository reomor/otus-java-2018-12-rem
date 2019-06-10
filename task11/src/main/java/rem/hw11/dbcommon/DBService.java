package rem.hw11.dbcommon;

import rem.hw11.domain.DataSet;

import java.util.List;

public interface DBService<T extends DataSet> {
    String getConnectionMetaData();

    void save(T dataSetEntity);

    T load(long id);

    List<T> loadAll();

    void closeConnection();

    Class<T> getType();
}
