package ogya.lokakarya.be.config.security;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.user.UserRepository;

@Component
public class SecurityUtil {
    @Autowired
    private UserRepository userRepo;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID currUserId = (UUID) auth.getPrincipal();
        Optional<User> userOpt = userRepo.findById(currUserId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Current User Couldn't be found!");
        }
        return userOpt.get();
    }
}
