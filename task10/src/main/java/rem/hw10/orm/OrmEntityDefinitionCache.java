package rem.hw10.orm;

import rem.hw10.domain.DataSet;

import java.util.HashMap;
import java.util.Map;

public class OrmEntityDefinitionCache {
    private Map<Class<? extends DataSet>, EntityDefinition> cache;

    private static class SingletonHolder {
        static final OrmEntityDefinitionCache HOLDER_INSTANCE = new OrmEntityDefinitionCache();
    }

    private OrmEntityDefinitionCache() {
        cache = new HashMap<>();
    }

    public static OrmEntityDefinitionCache getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    public EntityDefinition getDataSetEntityDefinition(Class<? extends DataSet> clazz) {
        EntityDefinition entityDefinition = cache.get(clazz);
        if (entityDefinition == null) {
            entityDefinition = new EntityDefinition(clazz);
            cache.put(clazz, entityDefinition);
        }
        return entityDefinition;
    }
}
