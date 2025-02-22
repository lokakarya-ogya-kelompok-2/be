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
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.jwt.JwtService;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.auth.LoginReq;
import ogya.lokakarya.be.dto.auth.LoginRes;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AuthService authSvc;

    @Autowired
    private JwtService jwtSvc;

    @SuppressWarnings("java:S1452")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq data) {
        log.info("Starting AuthController.login for user: {}", data.getEmailOrUsername());
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    data.getEmailOrUsername(), data.getPassword()));
        } catch (BadCredentialsException e) {
            log.warn("Login failed for user: {}", data.getEmailOrUsername());
            return ResponseDto.<Void>builder().success(false)
                    .message("Invalid (username/email)/password!").build()
                    .toResponse(HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = authSvc.loadUserByUsername(data.getEmailOrUsername());
        final String token = jwtSvc.generateToken((User) userDetails);
        UserDto userDto = new UserDto((User) userDetails, true, true, true);
        log.info("Ending AuthController.login for user: {}", data.getEmailOrUsername());
        return ResponseDto.<LoginRes>builder().success(true).content(new LoginRes(userDto, token))
                .message("Login successful!").build().toResponse(HttpStatus.OK);
    }
}
