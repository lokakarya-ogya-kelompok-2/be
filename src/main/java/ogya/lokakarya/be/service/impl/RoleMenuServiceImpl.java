package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.RoleMenu;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.MenuRepository;
import ogya.lokakarya.be.repository.RoleMenuRepository;
import ogya.lokakarya.be.service.RoleMenuService;

@Slf4j
@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    private RoleMenuRepository roleMenuRepo;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("java:S1192")
    @Transactional
    @Override
    public void update(Map<UUID, List<UUID>> roleMenuData) {
        log.info("Starting RoleMenuServiceImpl.update");
        roleMenuRepo.deleteAll();
        entityManager.flush();

        Optional<Menu> roleMenuAccessOpt = menuRepo.findByMenuName("role-menu#all");
        if (roleMenuAccessOpt.isEmpty()) {
            log.error("role-menu#all could not be found!");
            throw new ResponseException("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Menu roleMenuAccess = roleMenuAccessOpt.get();
        Optional<Menu> selfSummaryAccessOpt = menuRepo.findByMenuName("summary#read.self");
        if (selfSummaryAccessOpt.isEmpty()) {
            log.error("summary#read.self could not be found!");
            throw new ResponseException("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Menu selfSummaryAccess = selfSummaryAccessOpt.get();
        Optional<Menu> empSuggestionAccessOpt = menuRepo.findByMenuName("emp-suggestion#all");
        if (empSuggestionAccessOpt.isEmpty()) {
            log.error("emp-suggestion#all could not be found!");
            throw new ResponseException("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Menu empSuggestionAccess = empSuggestionAccessOpt.get();

        List<RoleMenu> roleMenus = new ArrayList<>();
        boolean atLeastOneRoleMustHaveRoleMenuAccess = false;

        for (Map.Entry<UUID, List<UUID>> entry : roleMenuData.entrySet()) {
            if (entry.getValue().contains(empSuggestionAccess.getId())
                    && !entry.getValue().contains(selfSummaryAccess.getId())) {
                throw new ResponseException(
                        "summary#read.self must be selected if emp-suggestion#all is selected!",
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }
            for (UUID menuId : entry.getValue()) {
                atLeastOneRoleMustHaveRoleMenuAccess = atLeastOneRoleMustHaveRoleMenuAccess
                        || menuId.equals(roleMenuAccess.getId());
                RoleMenu roleMenuEntity = new RoleMenu();
                Role roleEntity = new Role();
                roleEntity.setId(entry.getKey());
                roleMenuEntity.setRole(roleEntity);
                Menu menuEntity = new Menu();
                menuEntity.setId(menuId);
                roleMenuEntity.setMenu(menuEntity);
                roleMenus.add(roleMenuEntity);
            }
        }

        if (!atLeastOneRoleMustHaveRoleMenuAccess) {
            throw new ResponseException("At least one role muse have access to role-menu#all",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }


        roleMenuRepo.saveAll(roleMenus);
        log.info("Ending RoleMenuServiceImpl.update");
    }

}
