package ogya.lokakarya.be.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.config.security.jwt.JwtUtil;
import ogya.lokakarya.be.dto.auth.LoginDto;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.user.UserRepository;
import ogya.lokakarya.be.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(LoginDto data) {
        Optional<User> userOpt = userRepo.findByEmailAddress(data.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtUtil.generateToken(user);
    }

}
