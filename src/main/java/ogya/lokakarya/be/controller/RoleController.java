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
import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.dto.role.RoleFilter;
import ogya.lokakarya.be.dto.role.RoleReq;
import ogya.lokakarya.be.service.RoleService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/roles")
public class RoleController {
        @Autowired
        private RoleService roleService;

        @PostMapping
        public ResponseEntity<ResponseDto<RoleDto>> create(@RequestBody @Valid RoleReq data) {
                log.info("Starting RoleController.create");
                var createdRole = roleService.create(data);
                log.info("Ending RoleController.create");
                return ResponseDto.<RoleDto>builder().content(createdRole)
                                .message("Create role successful!").success(true).build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<RoleDto>>> list(
                        @RequestParam(name = "name_contains", required = false) String nameContains,
                        @RequestParam(name = "with_menus", required = false,
                                        defaultValue = "false") Boolean withMenus,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy) {
                log.info("Starting RoleController.list");
                RoleFilter filter = new RoleFilter();
                filter.setNameContains(nameContains);
                filter.setWithMenus(withMenus);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                List<RoleDto> response = roleService.getAllRole(filter);
                log.info("Ending RoleController.list");
                return ResponseDto.<List<RoleDto>>builder().content(response)
                                .message("Get all role successful!").success(true).build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<RoleDto>> getRoleById(@PathVariable UUID id) {
                log.info("Starting RoleController.get for id = {}", id);
                RoleDto response = roleService.getRoleById(id);
                log.info("Ending RoleController.get for id = {}", id);
                return ResponseDto.<RoleDto>builder().content(response)
                                .message(String.format("Get role with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<RoleDto>> updateRoleById(@PathVariable UUID id,
                        @RequestBody @Valid RoleReq createRole) {
                log.info("Starting RoleController.update for id = {}", id);
                RoleDto res = roleService.updateRoleById(id, createRole);
                log.info("Ending RoleController.update for id = {}", id);
                return ResponseDto.<RoleDto>builder().content(res)
                                .message(String.format("Update role with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteRoleById(@PathVariable UUID id) {
                log.info("Starting RoleController.delete for id = {}", id);
                roleService.deleteRoleById(id);
                log.info("Ending RoleController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true)
                                .message(String.format("Delete role with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
