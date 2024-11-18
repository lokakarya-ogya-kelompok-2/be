package ogya.lokakarya.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.auth.LoginDto;
import ogya.lokakarya.be.service.AuthService;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authSvc;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto data) {
        System.out.println("LOGIN DATA = " + data);
        String token = authSvc.login(data);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
