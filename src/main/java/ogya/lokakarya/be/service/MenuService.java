package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.menu.MenuDto;
import ogya.lokakarya.be.dto.menu.MenuFilter;
import ogya.lokakarya.be.dto.menu.MenuReq;

public interface MenuService {
    MenuDto create(MenuReq data);

    List<MenuDto> getAllMenus();

    MenuDto getMenuById(UUID id);

    MenuDto updateMenuById(UUID id, MenuReq menuReq);

    boolean deleteMenuById(UUID id);

    List<MenuDto> findWithFilter(MenuFilter filter);
}
