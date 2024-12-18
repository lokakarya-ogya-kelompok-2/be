package ogya.lokakarya.be.service.impl;

import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.UserRepository;
import ogya.lokakarya.be.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Starting AuthServiceImpl.loadUserByUsername");
        Optional<User> userOpt =
                userRepo.findByUsername(username).or(() -> userRepo.findByEmailAddress(username));
        log.info("Ending AuthServiceImpl.loadUserByUsername");
        return userOpt.orElseThrow(() -> new UsernameNotFoundException("user could not be found!"));
    }

    @Override
    public UserDetails loadByUserId(UUID id) {
        log.info("Starting AuthServiceImpl.loadByUserId");
        Optional<User> userOpt = userRepo.findById(id);
        log.info("Ending AuthServiceImpl.loadByUserId");
        return userOpt.orElseThrow(() -> new UsernameNotFoundException("user could not be found!"));
    }

}
