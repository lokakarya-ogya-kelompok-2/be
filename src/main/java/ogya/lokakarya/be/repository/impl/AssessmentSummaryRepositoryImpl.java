package ogya.lokakarya.be.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryFilter;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.FilterRepository;

public class AssessmentSummaryRepositoryImpl
        implements FilterRepository<AssessmentSummary, AssessmentSummaryFilter> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<AssessmentSummary> findAllByFilter(AssessmentSummaryFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AssessmentSummary> query = cb.createQuery(AssessmentSummary.class);
        Root<AssessmentSummary> root = query.from(AssessmentSummary.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getUserIds() != null) {
            Join<AssessmentSummary, User> assessmentSummaryUserJoin =
                    root.join("user", JoinType.LEFT);
            predicates.add(assessmentSummaryUserJoin.get("id").in(filter.getUserIds()));
        }

        if (filter.getYears() != null) {
            predicates.add(root.get("year").in(filter.getYears()));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
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

        query.distinct(true);
        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

}
