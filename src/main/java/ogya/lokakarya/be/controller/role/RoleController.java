package ogya.lokakarya.be.controller.role;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.role.CreateRole;
import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/roles")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody @Valid CreateRole data) {
        var createdRole = roleService.create(data);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllUser() {
        System.out.println("Get All Role");
        List<RoleDto> response = roleService.getAllRole();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable UUID id) {
        RoleDto response = roleService.getRoleById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole
            (@PathVariable UUID id, @RequestBody @Valid CreateRole createRole) {
    RoleDto res= roleService.updateRoleById(id, createRole);
    return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRole(@PathVariable UUID id) {
    boolean res= roleService.deleteRoleById(id);
    return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
