package rem.hw11.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import javax.persistence.Entity;

public class HibernateHelper {

    private HibernateHelper() {
    }

    public static Configuration getConfiguration() {
        return new Configuration();
    }

    public static Configuration getConfigurationWithAnnotatedClasses() {
        final Configuration hibernateConfiguration = new Configuration();
        Reflections reflections = new Reflections("rem.hw11.domain");
        for (Class<?> entityClass : reflections.getTypesAnnotatedWith(Entity.class)) {
            hibernateConfiguration.addAnnotatedClass(entityClass);
        }
        return hibernateConfiguration;
    }

    public static SessionFactory getSessionFactory(Configuration hibernateConfiguration) {
       return hibernateConfiguration.configure().buildSessionFactory();
    }
}
