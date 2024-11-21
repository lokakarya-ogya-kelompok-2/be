package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.dto.role.RoleReq;
import ogya.lokakarya.be.service.RoleService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/roles")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<ResponseDto<RoleDto>> create(@RequestBody @Valid RoleReq data) {
        var createdRole = roleService.create(data);
        return ResponseDto.<RoleDto>builder().content(createdRole)
                .message("Create Role successful!").success(true).build()
                .toResponse(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RoleDto>>> getAllUser() {
        System.out.println("Get All Role");
        List<RoleDto> response = roleService.getAllRole();
        return ResponseDto.<List<RoleDto>>builder().content(response)
                .message("Get All Role Successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RoleDto>> getRoleById(@PathVariable UUID id) {
        RoleDto response = roleService.getRoleById(id);
        return ResponseDto.<RoleDto>builder().content(response)
                .message(String.format("Get role with id %s successful!", id)).success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RoleDto>> updateRoleById
            (@PathVariable UUID id, @RequestBody @Valid RoleReq createRole) {
    RoleDto res= roleService.updateRoleById(id, createRole);
    return ResponseDto.<RoleDto>builder().content(res)
            .message(String.format("Update role with id %s successful!", id)).success(true)
            .build().toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteRoleById(@PathVariable UUID id) {
    roleService.deleteRoleById(id);
    return ResponseDto.<Void>builder().success(true)
            .message(String.format("Delete role with id %s successful!", id)).build()
            .toResponse(HttpStatus.OK);
    }
}
