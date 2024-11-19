package ogya.lokakarya.be.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.UserRepository;

@Component
public class SecurityUtil {
    @Autowired
    private UserRepository userRepo;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // String username = (String) auth.getPrincipal();
        // Optional<User> userOpt = userRepo.findByUsername(username);
        // if (userOpt.isEmpty()) {
        // throw new RuntimeException("Current User Couldn't be found!");
        // }
        // return userOpt.get();
        return (User) auth.getPrincipal();
    }
}
