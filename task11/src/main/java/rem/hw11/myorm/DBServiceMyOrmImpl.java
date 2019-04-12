package rem.hw11.myorm;

import rem.hw11.dao.DataSetDao;
import rem.hw11.dao.UserDataSetMyOrmDao;
import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.DataSet;

import javax.persistence.Entity;
import java.sql.*;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class DBServiceMyOrmImpl implements DBService {
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS public.USERDATASET (\n" +
            "  id        IDENTITY NOT NULL PRIMARY KEY,\n" +
            "  name VARCHAR(255),\n" +
            "  age       INTEGER\n" +
            ");";
    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS public.USERDATASET;";
    private final Connection connection;
    private final DataSetDao dataSetDao;

    public DBServiceMyOrmImpl(Connection connection) throws SQLException {
        this.connection = connection;
        this.dataSetDao = new UserDataSetMyOrmDao(connection);
        checkTablesExist();
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
    public <T extends DataSet> void save(T dataSetEntity) throws SQLException {
        dataSetDao.save(dataSetEntity);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        return (T) dataSetDao.load(id);
    }

    @Override
    public <T extends DataSet> List<T> loadAll(Class<T> clazz) throws SQLException {
        return (List<T>) dataSetDao.loadAll();
    }

    @Override
    public void createTables() throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            statement.executeQuery(CREATE_TABLE_USER);
        }
    }

    @Override
    public void deleteTables() throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE_USER);
        }
    }

    private void checkTablesExist() throws SQLException {
        final Set<Class<?>> classesByAnnotation = ReflectionHelper.getClassesByAnnotation(Entity.class);
        final Set<String> classesByAnnotationNames = classesByAnnotation.stream()
                .map(aClass -> aClass.getSimpleName().toUpperCase())
                .collect(Collectors.toSet());
        ResultSet res = connection.getMetaData()
                .getTables(null, null, null, new String[]{"TABLE"});
        while (res.next()) {
            final String tableName = res.getString("TABLE_NAME").toUpperCase();
            if(!classesByAnnotationNames.contains(tableName)) {
                throw new SQLException(String.format("Table %s doesn't exist", tableName));
            }
        }
    }

}
