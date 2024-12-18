package ogya.lokakarya.be.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.dto.division.DivisionFilter;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.repository.FilterRepository;

@Repository
public class DivisionRepositoryImpl implements FilterRepository<Division, DivisionFilter> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Division> findAllByFilter(DivisionFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Division> query = cb.createQuery(Division.class);
        Root<Division> root = query.from(Division.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getNameContains() != null) {
            predicates.add(cb.like(cb.lower(root.get("divisionName")),
                    "%" + filter.getNameContains().toLowerCase() + "%"));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        query.select(root).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

}
