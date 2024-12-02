package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.service.RoleMenuService;

@RequestMapping("/role-menu")
@RestController
public class RoleMenuController {
    @Autowired
    private RoleMenuService roleMenuSvc;

    @PutMapping
    ResponseEntity<ResponseDto<Void>> update(@RequestBody Map<UUID, List<UUID>> data) {
        System.out.println(data);
        roleMenuSvc.update(data);
        return ResponseDto.<Void>builder().success(true).message("Role-menu updated successfully!")
                .build().toResponse(HttpStatus.OK);
    }
}
