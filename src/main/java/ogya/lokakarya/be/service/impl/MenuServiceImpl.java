package ogya.lokakarya.be.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.menu.MenuDto;
import ogya.lokakarya.be.dto.menu.MenuFilter;
import ogya.lokakarya.be.dto.menu.MenuReq;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.MenuRepository;
import ogya.lokakarya.be.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public MenuDto create(MenuReq data) {
        Menu menuEntity = data.toEntity();
        User currentUser = securityUtil.getCurrentUser();
        menuEntity.setCreatedBy(currentUser);
        menuRepository.save(menuEntity);
        return new MenuDto(menuEntity, true, false);
    }

    @Override
    public List<MenuDto> getAllMenus(MenuFilter filter) {
        List<Menu> menuEntities = menuRepository.findAllByFilter(filter);

        return menuEntities.stream().map(
                menu -> new MenuDto(menu, filter.getWithCreatedBy(), filter.getWithUpdatedBy()))
                .toList();
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
}
