package ogya.lokakarya.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ogya.lokakarya.be.config.security.jwt.JwtUtil;
import ogya.lokakarya.be.dto.auth.LoginDto;
import ogya.lokakarya.be.service.AuthService;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AuthService authSvc;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto data) {
        String findBy = data.getEmail() == null ? data.getUsername() : data.getEmail();

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(findBy, data.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(String.format("invalid %s/password",
                    data.getEmail() == null ? "username" : "email"), HttpStatus.FORBIDDEN);
        }

        UserDetails userDetails = authSvc.loadUserByUsername(findBy);
        final String token = jwtUtil.generateToken(userDetails);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
