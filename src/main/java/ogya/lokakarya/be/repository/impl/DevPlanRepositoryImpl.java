package ogya.lokakarya.be.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.dto.devplan.DevPlanFilter;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.FilterRepository;

@Repository
public class DevPlanRepositoryImpl implements FilterRepository<DevPlan, DevPlanFilter> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<DevPlan> findAllByFilter(DevPlanFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DevPlan> query = cb.createQuery(DevPlan.class);
        Root<DevPlan> root = query.from(DevPlan.class);


        List<Predicate> predicates = new ArrayList<>();
        if (filter.getNameContains() != null) {
            predicates.add(cb.like(cb.lower(root.get("plan")),
                    "%" + filter.getNameContains().toLowerCase() + "%"));
        }
        if (filter.getEnabledOnly().booleanValue()) {
            predicates.add(cb.equal(root.get("enabled"), true));
        }
        if (filter.getWithCreatedBy().booleanValue() || filter.getWithUpdatedBy().booleanValue()) {
            Join<Menu, User> userJoin = null;
            if (filter.getWithCreatedBy().booleanValue()) {
                userJoin = root.join("createdBy", JoinType.LEFT);
            }
            if (filter.getWithUpdatedBy().booleanValue()) {
                if (userJoin == null) {
                    root.join("updatedBy", JoinType.LEFT);
                } else {
                    userJoin.join("updatedBy", JoinType.LEFT);
                }
            }
        }
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        query.distinct(true);
        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

}
