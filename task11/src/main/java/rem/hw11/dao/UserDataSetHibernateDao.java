package rem.hw11.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import rem.hw11.domain.UserDataSet;
import rem.hw11.executor.Executor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDataSetHibernateDao implements DataSetDao<UserDataSet> {
    private final SessionFactory sessionFactory;

    public UserDataSetHibernateDao(SessionFactory sessionFactory) {
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
                session.load(getType(), id));
    }

    @Override
    public List<UserDataSet> loadAll() {
        return Executor.query(sessionFactory, session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            final CriteriaQuery<UserDataSet> criteria = builder.createQuery(getType());
            final Query<UserDataSet> query = session.createQuery(criteria);
            return query.list();
        });
    }

    @Override
    public Class<UserDataSet> getType() {
        return UserDataSet.class;
    }
}
