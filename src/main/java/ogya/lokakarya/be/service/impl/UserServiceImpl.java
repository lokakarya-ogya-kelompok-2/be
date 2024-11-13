package ogya.lokakarya.be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.dto.user.CreateUser;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.user.UserRepository;
import ogya.lokakarya.be.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User create(CreateUser data) {
        return userRepo.save(data.toEntity());
    }

}
