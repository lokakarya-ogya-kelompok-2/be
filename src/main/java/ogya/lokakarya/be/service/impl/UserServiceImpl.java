package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.dto.user.CreateUser;
import ogya.lokakarya.be.dto.user.UserDto;
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

    @Override
    public List<UserDto> list() {
        List<User> userEntities = userRepo.findAll();
        List<UserDto> users = new ArrayList<>(userEntities.size());
        userEntities.forEach(user -> users.add(new UserDto(user, true, true)));
        return users;
    }

}
