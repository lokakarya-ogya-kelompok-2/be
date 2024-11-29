package ogya.lokakarya.be.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.UserRepository;
import ogya.lokakarya.be.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt =
                userRepo.findByUsername(username).or(() -> userRepo.findByEmailAddress(username));
        return userOpt.orElseThrow(() -> new UsernameNotFoundException("user could not be found!"));
    }

    @Override
    public UserDetails loadByUserId(UUID id) {
        Optional<User> userOpt = userRepo.findById(id);
        return userOpt.orElseThrow(() -> new UsernameNotFoundException("user could not be found!"));
    }

}
