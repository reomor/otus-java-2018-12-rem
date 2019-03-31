package rem.hw10;

import org.reflections.Reflections;
import rem.hw10.annotation.DataSetEntity;
import rem.hw10.dbcommon.ConnectionHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        try(final Connection connection = ConnectionHelper.getConnection()) {
            System.out.println(connection.getMetaData().getDriverName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // dbservice
        Reflections reflections = new Reflections("rem.hw10");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(DataSetEntity.class);

        for (Class<?> annotatedClass : annotatedClasses) {
            System.out.println(annotatedClass.getName());
            System.out.println(annotatedClass.getSimpleName());
            Class clazz = annotatedClass;
            while (clazz != null && !Object.class.equals(clazz)) {
                for (Field declaredField : clazz.getDeclaredFields()) {
                    System.out.println(declaredField.getName());
                }
                clazz = clazz.getSuperclass();
            }
        }
    }
}
