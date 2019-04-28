package rem.hw12.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import rem.hw12.dao.DataSetDao;
import rem.hw12.domain.UserDataSet;
import rem.hw12.executor.Executor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDataSetHibernateDaoImpl implements DataSetDao<UserDataSet> {
    private final SessionFactory sessionFactory;

    public UserDataSetHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(UserDataSet dataSetEntity) {
        Executor.update(sessionFactory, session ->
                session.save(dataSetEntity));
    }

    @Override
    public UserDataSet load(long id) {
        return Executor.query(sessionFactory, session ->
                //get instead of load, because get return null if not exist, load throws exception
                session.get(getType(), id));
    }

    @Override
    public List<UserDataSet> loadAll() {
        return Executor.query(sessionFactory, session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            final CriteriaQuery<UserDataSet> criteria = builder.createQuery(getType());
            criteria.from(getType());
            final Query<UserDataSet> query = session.createQuery(criteria);
            return query.list();
        });
    }

    @Override
    public Class<UserDataSet> getType() {
        return UserDataSet.class;
    }
}
