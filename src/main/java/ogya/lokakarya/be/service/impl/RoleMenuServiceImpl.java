package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.RoleMenu;
import ogya.lokakarya.be.repository.RoleMenuRepository;
import ogya.lokakarya.be.service.RoleMenuService;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuRepository roleMenuRepo;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public void update(Map<UUID, List<UUID>> roleMenuData) {
        roleMenuRepo.deleteAll();
        entityManager.flush();

        List<RoleMenu> roleMenus = new ArrayList<>();
        roleMenuData.forEach((roleId, menus) -> menus.forEach(menuId -> {
            RoleMenu roleMenuEntity = new RoleMenu();
            Role roleEntity = new Role();
            roleEntity.setId(roleId);
            roleMenuEntity.setRole(roleEntity);
            Menu menuEntity = new Menu();
            menuEntity.setId(menuId);
            roleMenuEntity.setMenu(menuEntity);
            roleMenus.add(roleMenuEntity);
        }));

        roleMenuRepo.saveAll(roleMenus);
    }

}
