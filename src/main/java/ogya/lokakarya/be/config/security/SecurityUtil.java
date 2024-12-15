package ogya.lokakarya.be.config.security;

import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new ResponseException("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }
        return (User) auth.getPrincipal();
    }
}
