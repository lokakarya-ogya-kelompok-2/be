package ogya.lokakarya.be.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.user.CreateUser;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.service.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userSvc;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid CreateUser data) {
        var createdUser = userSvc.create(data);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
