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
import ogya.lokakarya.be.dto.menu.MenuFilter;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.RoleMenu;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.entity.UserRole;
import ogya.lokakarya.be.repository.FilterRepository;

@SuppressWarnings("java:S3776")
@Repository
public class MenuRepositoryImpl implements FilterRepository<Menu, MenuFilter> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Menu> findAllByFilter(MenuFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Menu> query = cb.createQuery(Menu.class);
        Root<Menu> root = query.from(Menu.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getRoleNames() != null || filter.getUserId() != null) {
            Join<Menu, RoleMenu> roleMenuJoin = root.join("roleMenus", JoinType.LEFT);
            Join<RoleMenu, Role> roleJoin = roleMenuJoin.join("role", JoinType.LEFT);
            if (filter.getUserId() != null) {
                Join<Role, UserRole> userRoleJoin = roleJoin.join("userRoles", JoinType.LEFT);
                Join<UserRole, User> userJoin = userRoleJoin.join("user", JoinType.LEFT);
                predicates.add(cb.equal(userJoin.get("id"), filter.getUserId()));
            }
            if (filter.getRoleNames() != null && !filter.getRoleNames().isEmpty()) {
                predicates.add(roleJoin.get("roleName").in(filter.getRoleNames()));
            }
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        query.select(root).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

}
