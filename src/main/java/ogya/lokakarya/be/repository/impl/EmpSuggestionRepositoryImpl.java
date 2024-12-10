package ogya.lokakarya.be.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionFilter;
import ogya.lokakarya.be.entity.EmpSuggestion;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpSuggestionRepositoryImpl
        implements FilterRepository<EmpSuggestion, EmpSuggestionFilter> {
@Autowired
    private EntityManager entityManager;

    @Override
    public List<EmpSuggestion> findAllByFilter(EmpSuggestionFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmpSuggestion> query = cb.createQuery(EmpSuggestion.class);
        Root<EmpSuggestion> root = query.from(EmpSuggestion.class);

        List<Predicate> predicates = new ArrayList<>();
        System.out.println(filter.getUserIds()+"AAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(filter);
        if (filter.getUserIds() != null) {
            Join<EmpSuggestion, User> suggestionUserJoin = root.join("user", JoinType.LEFT);
            predicates.add(suggestionUserJoin.get("id").in(filter.getUserIds()));
        }

        if (filter.getYears() != null) {
            predicates.add(root.get("assessmentYear").in(filter.getYears()));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        if (filter.getWithCreatedBy().booleanValue() || filter.getWithUpdatedBy().booleanValue()) {
            Join<EmpSuggestion, User> userJoin = null;
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
        query.distinct(true);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }
}
