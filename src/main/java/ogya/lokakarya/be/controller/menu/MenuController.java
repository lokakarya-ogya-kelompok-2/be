package ogya.lokakarya.be.controller.menu;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.division.CreateDivision;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.menu.CreateMenu;
import ogya.lokakarya.be.dto.menu.MenuDto;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/menu")
@RestController
public class MenuController {
    @Autowired
    MenuService menuService;

    @PostMapping
    public ResponseEntity<Menu> create(@RequestBody @Valid CreateMenu data) {
        var createMenu= menuService.create(data);
        return new ResponseEntity<>(createMenu, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<MenuDto>> getAllMenus() {
        System.out.println("Get All Menus");
        List<MenuDto> response = menuService.getAllMenus();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> getMenuById(@PathVariable UUID id) {
        MenuDto response = menuService.getMenuById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MenuDto> updateDivisionById
            (@PathVariable UUID id, @RequestBody @Valid CreateMenu createMenu) {
        MenuDto res= menuService.updateMenuById(id, createMenu);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteMenuById(@PathVariable UUID id) {
        boolean res= menuService.deleteMenuById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
