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
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillFilter;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.repository.FilterRepository;

@Repository
public class AttitudeSkillRepositoryImpl
        implements FilterRepository<AttitudeSkill, AttitudeSkillFilter> {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("java:S3776")
    @Override
    public List<AttitudeSkill> findAllByFilter(AttitudeSkillFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AttitudeSkill> query = cb.createQuery(AttitudeSkill.class);
        Root<AttitudeSkill> root = query.from(AttitudeSkill.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getNameContains() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")),
                    "%" + filter.getNameContains().toLowerCase() + "%"));
        }
        if (filter.getGroupIds() != null || filter.getWithGroup().booleanValue()) {
            Join<AttitudeSkill, GroupAttitudeSkill> attitudeSkillGroupAttitudeSkillJoin =
                    root.join("groupAttitudeSkill", JoinType.LEFT);
            if (filter.getGroupIds() != null) {
                predicates.add(
                        attitudeSkillGroupAttitudeSkillJoin.get("id").in(filter.getGroupIds()));
            }
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
