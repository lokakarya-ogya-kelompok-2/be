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
import ogya.lokakarya.be.dto.achievement.AchievementFilter;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.FilterRepository;

@Repository
public class AchievementRepositoryImpl implements FilterRepository<Achievement, AchievementFilter> {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("java:S3776")
    @Override
    public List<Achievement> findAllByFilter(AchievementFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Achievement> query = cb.createQuery(Achievement.class);
        Root<Achievement> root = query.from(Achievement.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getNameContains() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")),
                    "%" + filter.getNameContains().toLowerCase() + "%"));
        }
        if (filter.getGroupIds() != null || filter.getWithGroup().booleanValue()) {
            Join<Achievement, GroupAchievement> achievementGroupAchievementJoin =
                    root.join("groupAchievement", JoinType.LEFT);
            if (filter.getGroupIds() != null) {
                predicates.add(achievementGroupAchievementJoin.in(filter.getGroupIds()));
            }
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
        query.select(root).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }
}
