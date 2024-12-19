package ogya.lokakarya.be.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.dto.role.RoleFilter;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.repository.FilterRepository;

public class RoleRepositoryImpl implements FilterRepository<Role, RoleFilter> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Role> findAllByFilter(RoleFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> query = cb.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getNameContains() != null) {
            predicates.add(
                    cb.like(cb.lower(root.get("roleName")), "%" + filter.getNameContains() + "%"));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        query.select(root).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

}
