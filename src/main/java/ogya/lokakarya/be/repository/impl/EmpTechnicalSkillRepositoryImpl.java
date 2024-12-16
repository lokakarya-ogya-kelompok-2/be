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
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillFilter;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.FilterRepository;

@Repository
public class EmpTechnicalSkillRepositoryImpl
        implements FilterRepository<EmpTechnicalSkill, EmpTechnicalSkillFilter> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<EmpTechnicalSkill> findAllByFilter(EmpTechnicalSkillFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmpTechnicalSkill> query = cb.createQuery(EmpTechnicalSkill.class);
        Root<EmpTechnicalSkill> root = query.from(EmpTechnicalSkill.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getUserIds() != null) {
            Join<EmpTechnicalSkill, User> techSkillUserJoin = root.join("user", JoinType.LEFT);
            predicates.add(techSkillUserJoin.get("id").in(filter.getUserIds()));
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
