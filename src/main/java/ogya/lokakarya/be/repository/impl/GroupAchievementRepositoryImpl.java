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
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementFilter;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.repository.FilterRepository;

@Repository
public class GroupAchievementRepositoryImpl
        implements FilterRepository<GroupAchievement, GroupAchievementFilter> {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings({"java:S3776", "java:S1192", "java:S6541"})
    @Override
    public List<GroupAchievement> findAllByFilter(GroupAchievementFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GroupAchievement> query = cb.createQuery(GroupAchievement.class);
        Root<GroupAchievement> root = query.from(GroupAchievement.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getNameContains() != null) {
            predicates.add(cb.like(cb.lower(root.get("groupName")),
                    "%" + filter.getNameContains().toLowerCase() + "%"));
        }
        if (filter.getMinWeight() != null && filter.getMaxWeight() != null) {
            predicates.add(cb.between(root.get("percentage"), filter.getMinWeight(),
                    filter.getMaxWeight()));
        } else if (filter.getMinWeight() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("percentage"), filter.getMinWeight()));
        } else if (filter.getMaxWeight() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("percentage"), filter.getMaxWeight()));
        }
        if (filter.getEnabledOnly().booleanValue()) {
            predicates.add(cb.equal(root.get("enabled"), true));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        query.select(root).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

}
