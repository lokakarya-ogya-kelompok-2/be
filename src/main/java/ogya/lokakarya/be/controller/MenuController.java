package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.menu.MenuDto;
import ogya.lokakarya.be.dto.menu.MenuFilter;
import ogya.lokakarya.be.dto.menu.MenuReq;
import ogya.lokakarya.be.service.MenuService;


@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/menus")
@RestController
public class MenuController {
    @Autowired
    MenuService menuService;

    @PostMapping
    public ResponseEntity<ResponseDto<MenuDto>> create(@RequestBody @Valid MenuReq data) {
        var createMenu = menuService.create(data);
        return ResponseDto.<MenuDto>builder().content(createMenu).message("Create menu successful!")
                .success(true).build().toResponse(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<MenuDto>>> getAllMenus() {
        System.out.println("Get All Menus");
        List<MenuDto> response = menuService.getAllMenus();
        return ResponseDto.<List<MenuDto>>builder().content(response)
                .message("Get all menus successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MenuDto>> getMenuById(@PathVariable UUID id) {
        MenuDto response = menuService.getMenuById(id);
        return ResponseDto.<MenuDto>builder().content(response)
                .message(String.format("Get menu with id %s successful!", id)).success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<MenuDto>> updateDivisionById(@PathVariable UUID id,
            @RequestBody @Valid MenuReq menuReq) {
        MenuDto res = menuService.updateMenuById(id, menuReq);
        return ResponseDto.<MenuDto>builder().content(res)
                .message(String.format("Update menu with id %s successful!", id)).success(true)
                .build().toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteMenuById(@PathVariable UUID id) {
        menuService.deleteMenuById(id);
        return ResponseDto.<Void>builder().success(true)
                .message(String.format("Delete menu with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<MenuDto>>> listWithFilter(
            @RequestParam(name = "user_id", required = false) UUID userId,
            @RequestParam(name = "role_names", required = false) List<String> roleNames) {
        MenuFilter menuFilter = new MenuFilter();
        menuFilter.setUserId(userId);
        menuFilter.setRoleNames(roleNames);
        var menus = menuService.findWithFilter(menuFilter);
        return ResponseDto.<List<MenuDto>>builder().success(true).content(menus)
                .message("list menu with filter successful!").build().toResponse(HttpStatus.OK);
    }

}
