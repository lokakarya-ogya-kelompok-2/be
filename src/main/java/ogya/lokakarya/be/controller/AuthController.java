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
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.auth.LoginReq;
import ogya.lokakarya.be.dto.auth.LoginRes;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.User;
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

    @SuppressWarnings("java:S1452")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq data) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    data.getEmailOrUsername(), data.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseDto.<Void>builder().success(false)
                    .message("Invalid (username/email)/password!").build()
                    .toResponse(HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = authSvc.loadUserByUsername(data.getEmailOrUsername());
        final String token = jwtUtil.generateToken(userDetails);
        UserDto userDto = new UserDto((User) userDetails, true, true, true);
        return ResponseDto.<LoginRes>builder().success(true).content(new LoginRes(userDto, token))
                .message("Login successful!").build().toResponse(HttpStatus.OK);
    }
}
