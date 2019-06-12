package rem.hw16.dbserver.dao;

import rem.hw16.dbserver.domain.DataSet;

import java.util.List;

public interface DataSetDao<T extends DataSet> {
    void save(T dataSetEntity);

    T load(long id);

    List<T> loadAll();

    Class<T> getType();
}
