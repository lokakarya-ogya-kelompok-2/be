package ogya.lokakarya.be.controller.role;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.Role.CreateRole;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
