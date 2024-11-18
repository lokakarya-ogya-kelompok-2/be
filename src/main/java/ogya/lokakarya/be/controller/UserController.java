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
import ogya.lokakarya.be.dto.user.CreateUserDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.service.UserService;



@RequestMapping("/users")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userSvc;


    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid CreateUserDto data) {
        var createdUser = userSvc.create(data);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> list() {
        var users = userSvc.list();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable UUID id) {
        return new ResponseEntity<>(userSvc.get(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @RequestBody CreateUserDto data) {
        var updatedUser = userSvc.update(id, data);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
