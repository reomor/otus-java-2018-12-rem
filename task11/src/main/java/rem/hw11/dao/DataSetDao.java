package rem.hw11.dao;

import rem.hw11.domain.DataSet;

import java.sql.SQLException;
import java.util.List;


public interface DataSetDao<T extends DataSet> {
    void save(T dataSetEntity) throws SQLException;

    T load(long id) throws SQLException;

    List<T> loadAll() throws SQLException;

    Class<T> getType();
}
