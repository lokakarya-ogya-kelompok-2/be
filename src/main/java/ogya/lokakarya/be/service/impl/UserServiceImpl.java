package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.dto.user.CreateUserDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.role.RoleRepository;
import ogya.lokakarya.be.repository.user.UserRepository;
import ogya.lokakarya.be.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public UserDto create(CreateUserDto data) {
        User userEntity = data.toEntity();
        Set<Role> roles;
        if ((data.getRoles() != null) && !data.getRoles().isEmpty()) {
            roles = new HashSet<>(data.getRoles().size());
            data.getRoles().forEach(roleId -> {
                Optional<Role> roleOpt = roleRepo.findById(roleId);
                if (roleOpt.isEmpty()) {
                    throw new RuntimeException(String.format("Role with id %s could not be found!",
                            roleId.toString()));
                }
                roles.add(roleOpt.get());
            });
            userEntity.setRoles(roles);
        }
        userEntity = userRepo.save(userEntity);
        return new UserDto(userEntity, false, false);
    }

    @Override
    public List<UserDto> list() {
        List<User> userEntities = userRepo.findAll();
        List<UserDto> users = new ArrayList<>(userEntities.size());
        userEntities.forEach(user -> users.add(new UserDto(user, true, true)));
        return users;
    }

    @Override
    public UserDto get(UUID id) {
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("USER WITH GIVEN ID COULD NOT BE FOUND");
        }
        return new UserDto(userOpt.get(), false, false);
    }

}
