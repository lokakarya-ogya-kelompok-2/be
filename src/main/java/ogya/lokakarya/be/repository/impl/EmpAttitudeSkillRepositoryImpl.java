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
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillFilter;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.FilterRepository;

@Repository
public class EmpAttitudeSkillRepositoryImpl
        implements FilterRepository<EmpAttitudeSkill, EmpAttitudeSkillFilter> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<EmpAttitudeSkill> findAllByFilter(EmpAttitudeSkillFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmpAttitudeSkill> query = cb.createQuery(EmpAttitudeSkill.class);
        Root<EmpAttitudeSkill> root = query.from(EmpAttitudeSkill.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getUserIds() != null) {
            Join<EmpAttitudeSkill, User> attitudeSkillUserJoin = root.join("user", JoinType.LEFT);
            predicates.add(attitudeSkillUserJoin.get("id").in(filter.getUserIds()));
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
