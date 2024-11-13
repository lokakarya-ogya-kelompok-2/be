package ogya.lokakarya.be.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.dto.user.Create;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.user.UserRepository;
import ogya.lokakarya.be.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User create(Create data) {
        return userRepo.save(data.toEntity());
    }

}
