package ogya.lokakarya.be.repository.impl;

import java.sql.Date;
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
import ogya.lokakarya.be.dto.user.UserFilter;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.FilterRepository;

@Repository
public class UserRepositoryImpl implements FilterRepository<User, UserFilter> {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings({"java:S3776", "java:S1192", "java:S6541"})
    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Join<User, Division> userDivisionJoin = root.join("division", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getUsernameContains() != null) {
            predicates.add(cb.like(cb.lower(root.get("username")),
                    "%" + filter.getUsernameContains().toLowerCase() + "%"));
        }
        if (filter.getNameContains() != null) {
            predicates.add(cb.like(cb.lower(root.get("fullName")),
                    "%" + filter.getNameContains().toLowerCase() + "%"));
        }
        if (filter.getPositionContains() != null) {
            predicates.add(cb.like(cb.lower(root.get("position")),
                    "%" + filter.getPositionContains().toLowerCase() + "%"));
        }
        if (filter.getEmailContains() != null) {
            predicates.add(cb.like(cb.lower(root.get("emailAddress")),
                    "%" + filter.getEmailContains().toLowerCase() + "%"));
        }
        if (filter.getMinJoinDate() != null && filter.getMaxJoinDate() != null) {
            predicates.add(cb.between(root.get("joinDate"), Date.valueOf(filter.getMinJoinDate()),
                    Date.valueOf(filter.getMaxJoinDate())));
        } else if (filter.getMinJoinDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("joinDate"),
                    Date.valueOf(filter.getMinJoinDate())));
        } else if (filter.getMaxJoinDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("joinDate"),
                    Date.valueOf(filter.getMaxJoinDate())));
        }
        if (filter.getEmployeeStatus() != null) {
            predicates.add(cb.equal(root.get("employeeStatus"), filter.getEmployeeStatus()));
        }
        if (filter.getDivisionNameContains() != null) {
            predicates.add(cb.like(cb.lower(userDivisionJoin.get("divisionName")),
                    "%" + filter.getDivisionNameContains().toLowerCase() + "%"));
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
