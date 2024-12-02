package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.menu.MenuDto;
import ogya.lokakarya.be.dto.menu.MenuFilter;
import ogya.lokakarya.be.dto.menu.MenuReq;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.RoleMenu;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.entity.UserRole;
import ogya.lokakarya.be.repository.MenuRepository;
import ogya.lokakarya.be.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public MenuDto create(MenuReq data) {
        Menu menuEntity = data.toEntity();
        User currentUser = securityUtil.getCurrentUser();
        menuEntity.setCreatedBy(currentUser);
        menuRepository.save(menuEntity);
        return new MenuDto(menuEntity, true, false);
    }

    @Override
    public List<MenuDto> getAllMenus() {
        List<MenuDto> listResult = new ArrayList<>();
        List<Menu> roleList = menuRepository.findAll();
        for (Menu menu : roleList) {
            MenuDto result = convertToDto(menu);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public MenuDto getMenuById(UUID id) {
        Optional<Menu> listData;
        try {
            listData = menuRepository.findById(id);
            System.out.println(listData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        Menu data = listData.get();
        return convertToDto(data);
    }

    @Override
    public MenuDto updateMenuById(UUID id, MenuReq menuReq) {
        Optional<Menu> listData = menuRepository.findById(id);
        if (listData.isPresent()) {
            Menu menu = listData.get();
            if (!menuReq.getMenuName().isBlank()) {
                menu.setMenuName(menuReq.getMenuName());
            }
            MenuDto menuDto = convertToDto(menu);
            menuRepository.save(menu);
            return menuDto;
        }
        return null;
    }

    @Override
    public boolean deleteMenuById(UUID id) {
        Optional<Menu> listData = menuRepository.findById(id);
        if (listData.isPresent()) {
            menuRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private MenuDto convertToDto(Menu data) {
        return new MenuDto(data, true, true);
    }

    // https://stackoverflow.com/questions/41222061/sql-query-builder-in-jpa-query/41222195#41222195
    @Override
    public List<MenuDto> findWithFilter(MenuFilter filter) {
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

        query.distinct(true);
        query.select(root);

        List<Menu> menuEntities = entityManager.createQuery(query).getResultList();
        List<MenuDto> menus = new ArrayList<>(menuEntities.size());
        menuEntities.forEach(menu -> menus
                .add(new MenuDto(menu, filter.getWithCreatedBy(), filter.getWithUpdatedBy())));
        return menus;
    }
}
