package rem.hw12.dao;

import rem.hw12.domain.DataSet;

import java.util.List;

public interface DataSetDao<T extends DataSet> {
    void save(T dataSetEntity);

    T load(long id);

    List<T> loadAll();

    Class<T> getType();
}
