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
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.menu.MenuDto;
import ogya.lokakarya.be.dto.menu.MenuFilter;
import ogya.lokakarya.be.dto.menu.MenuReq;
import ogya.lokakarya.be.service.MenuService;


@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/menus")
public class MenuController {
        @Autowired
        MenuService menuService;

        @PostMapping
        public ResponseEntity<ResponseDto<MenuDto>> create(@RequestBody @Valid MenuReq data) {
                log.info("Starting MenuController.create");
                var createMenu = menuService.create(data);
                log.info("Ending MenuController.create");
                return ResponseDto.<MenuDto>builder().content(createMenu)
                                .message("Create menu successful!").success(true).build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<MenuDto>>> list(
                        @RequestParam(name = "user_id", required = false) UUID userId,
                        @RequestParam(name = "role_names", required = false) List<String> roleNames,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy) {
                log.info("Starting MenuController.list");
                MenuFilter filter = new MenuFilter();
                filter.setUserId(userId);
                filter.setRoleNames(roleNames);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                var data = menuService.getAllMenus(filter);
                log.info("Ending MenuController.list");
                return ResponseDto.<List<MenuDto>>builder().success(true).content(data)
                                .message("list menu with filter successful!").build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<MenuDto>> getMenuById(@PathVariable UUID id) {
                log.info("Starting MenuController.get for id = {}", id);
                MenuDto response = menuService.getMenuById(id);
                log.info("Ending MenuController.get for id = {}", id);
                return ResponseDto.<MenuDto>builder().content(response)
                                .message(String.format("Get menu with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<MenuDto>> updateById(@PathVariable UUID id,
                        @RequestBody @Valid MenuReq menuReq) {
                log.info("Starting MenuController.update for id = {}", id);
                MenuDto res = menuService.updateMenuById(id, menuReq);
                log.info("Ending MenuController.update for id = {}", id);
                return ResponseDto.<MenuDto>builder().content(res)
                                .message(String.format("Update menu with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteMenuById(@PathVariable UUID id) {
                log.info("Starting MenuController.delete for id = {}", id);
                menuService.deleteMenuById(id);
                log.info("Ending MenuController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true)
                                .message(String.format("Delete menu with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
