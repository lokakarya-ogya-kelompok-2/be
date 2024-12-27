package ogya.lokakarya.be.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.UserRepository;
import ogya.lokakarya.be.service.AuthService;

@SuppressWarnings("java:S1192")
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Starting AuthServiceImpl.loadUserByUsername");
        Optional<User> userOpt = userRepo.findByUsernameIgnoreCase(username)
                .or(() -> userRepo.findByEmailIgnoreCase(username));
        UserDetails user = userOpt
                .orElseThrow(() -> new UsernameNotFoundException("user could not be found!"));
        if (!user.isEnabled()) {
            throw new UsernameNotFoundException("user could not be found!");
        }
        System.out.println("IS USER ENABLED? (ATAS) => " + user.isEnabled());
        log.info("Ending AuthServiceImpl.loadUserByUsername");
        return user;
    }

    @Override
    public UserDetails loadByUserId(UUID id) {
        log.info("Starting AuthServiceImpl.loadByUserId");
        Optional<User> userOpt = userRepo.findById(id);
        UserDetails user = userOpt
                .orElseThrow(() -> new UsernameNotFoundException("user could not be found!"));
        if (!user.isEnabled()) {
            throw new UsernameNotFoundException("user could not be found!");
        }
        System.out.println("IS USER ENABLED? (BAWAH) => " + user.isEnabled());
        log.info("Ending AuthServiceImpl.loadByUserId");
        return user;
    }

}
