package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.menu.CreateMenu;
import ogya.lokakarya.be.dto.menu.MenuDto;
import ogya.lokakarya.be.entity.Menu;
import java.util.List;
import java.util.UUID;

public interface MenuService {
    Menu create(CreateMenu data);
    List<MenuDto> getAllMenus();
    MenuDto getMenuById(UUID id);
    MenuDto updateMenuById(UUID id, CreateMenu createMenu);
    boolean deleteMenuById(UUID id);
}
