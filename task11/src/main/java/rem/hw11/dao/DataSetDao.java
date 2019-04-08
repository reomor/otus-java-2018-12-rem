package rem.hw11.dao;

import rem.hw11.domain.DataSet;

import java.sql.SQLException;
import java.util.List;


public interface DataSetDao {
    <T extends DataSet> void save(T dataSetEntity) throws SQLException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

    <T extends DataSet> List<T> loadAll(Class<T> clazz) throws SQLException;
}
