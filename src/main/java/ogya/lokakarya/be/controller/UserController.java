package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.dto.user.UserReq;
import ogya.lokakarya.be.service.UserService;



@RequestMapping("/users")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userSvc;


    @PostMapping
    public ResponseEntity<ResponseDto<UserDto>> create(@RequestBody @Valid UserReq data) {
        var createdUser = userSvc.create(data);
        return ResponseDto.<UserDto>builder().content(createdUser)
                .message("Create user successful!").success(true).build()
                .toResponse(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<UserDto>>> list() {
        var users = userSvc.list();
        return ResponseDto.<List<UserDto>>builder().content(users)
                .message("List all user successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserDto>> get(@PathVariable UUID id) {
        var user = userSvc.get(id);
        return ResponseDto.<UserDto>builder().content(user)
                .message(String.format("Get user with id %s successful!", id)).success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<UserDto>> update(@PathVariable UUID id,
            @RequestBody @Valid UserReq data) {
        var updatedUser = userSvc.update(id, data);

        return ResponseDto.<UserDto>builder().content(updatedUser)
                .message(String.format("Update user with id %s successful!", id)).success(true)
                .build().toResponse(HttpStatus.OK);
    }
}
