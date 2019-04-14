package rem.hw11.myorm;

import rem.hw11.dao.DataSetDao;
import rem.hw11.dbcommon.ConnectionHelper;
import rem.hw11.dbcommon.DBService;
import rem.hw11.domain.UserDataSet;

import javax.persistence.Entity;
import java.sql.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DBServiceMyOrmImpl implements DBService<UserDataSet> {
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS public.USERDATASET (\n" +
            "  id        IDENTITY NOT NULL PRIMARY KEY,\n" +
            "  name VARCHAR(255),\n" +
            "  age       INTEGER,\n" +
            "  address_id   INTEGER FOREIGN KEY REFERENCES ADDRESSDATASET(id)" +
            ");";
    private static final String CREATE_TABLE_ADDRESS = "CREATE TABLE IF NOT EXISTS public.ADDRESSDATASET (\n" +
            "  id        IDENTITY NOT NULL PRIMARY KEY,\n" +
            "  street VARCHAR(255),\n" +
            ");";
    private static final String CREATE_TABLE_PHONE = "CREATE TABLE IF NOT EXISTS public.PHONEDATASET (\n" +
            "  id        IDENTITY NOT NULL PRIMARY KEY,\n" +
            "  number VARCHAR(255),\n" +
            "  user_id INTEGER FOREIGN KEY REFERENCES USERDATASET(id)\n" +
            ");";
    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS public.USERDATASET;";
    private static final String DROP_TABLE_ADDRESS = "DROP TABLE IF EXISTS public.ADDRESSDATASET;";
    private static final String DROP_TABLE_PHONE = "DROP TABLE IF EXISTS public.PHONEDATASET;";
    private final Connection connection;
    private final DataSetDao dataSetDao;

    public DBServiceMyOrmImpl(Connection connection) throws SQLException {
        this.connection = connection;
        this.dataSetDao = new UserDataSetMyOrmDaoImpl(connection);
        checkTablesExist();
    }

    @Override
    public String getConnectionMetaData() throws SQLException {
        return ConnectionHelper.getConnectionMetadata(connection);
    }

    @Override
    public void save(UserDataSet dataSetEntity) throws SQLException {
        dataSetDao.save(dataSetEntity);
    }

    @Override
    public UserDataSet load(long id) throws SQLException {
        return (UserDataSet) dataSetDao.load(id);
    }

    @Override
    public List<UserDataSet> loadAll() throws SQLException {
        return dataSetDao.loadAll();
    }

    @Override
    public void createTables() throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            statement.executeQuery(CREATE_TABLE_ADDRESS);
            statement.executeQuery(CREATE_TABLE_USER);
            statement.executeQuery(CREATE_TABLE_PHONE);
        }
    }

    @Override
    public void deleteTables() throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE_PHONE);
            statement.executeUpdate(DROP_TABLE_USER);
            statement.executeUpdate(DROP_TABLE_ADDRESS);
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<UserDataSet> getType() {
        return UserDataSet.class;
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
