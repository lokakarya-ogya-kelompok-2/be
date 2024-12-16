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
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillFilter;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.EmpAchievementSkill;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.FilterRepository;

@Repository
public class EmpAchievementSkillRepositoryImpl
        implements FilterRepository<EmpAchievementSkill, EmpAchievementSkillFilter> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<EmpAchievementSkill> findAllByFilter(EmpAchievementSkillFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmpAchievementSkill> query = cb.createQuery(EmpAchievementSkill.class);
        Root<EmpAchievementSkill> root = query.from(EmpAchievementSkill.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getUserIds() != null) {
            Join<EmpAchievementSkill, User> achievementUserJoin = root.join("user", JoinType.LEFT);
            predicates.add(achievementUserJoin.get("id").in(filter.getUserIds()));
        }
        if (filter.getEnabledOnly().booleanValue()) {
            Join<EmpAchievementSkill, Achievement> empAchievementAchievementJoin =
                    root.join("achievement", JoinType.LEFT);
            Join<Achievement, GroupAchievement> achievementGroupAchievementJoin =
                    empAchievementAchievementJoin.join("groupAchievement", JoinType.LEFT);
            predicates.add(cb.equal(empAchievementAchievementJoin.get("enabled"), true));
            predicates.add(cb.equal(achievementGroupAchievementJoin.get("enabled"), true));
        }
        if (filter.getYears() != null) {
            predicates.add(root.get("assessmentYear").in(filter.getYears()));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        query.select(root).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }
}
