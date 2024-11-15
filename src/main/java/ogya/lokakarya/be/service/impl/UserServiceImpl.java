package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ogya.lokakarya.be.dto.user.CreateUserDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.entity.UserRole;
import ogya.lokakarya.be.repository.UserRoleRepository;
import ogya.lokakarya.be.repository.role.RoleRepository;
import ogya.lokakarya.be.repository.user.UserRepository;
import ogya.lokakarya.be.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRoleRepository userRoleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto create(CreateUserDto data) {
        User userEntity = data.toEntity();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID hrId = (UUID) auth.getPrincipal();
        Optional<User> hrOpt = userRepo.findById(hrId);
        if (hrOpt.isEmpty()) {
            throw new RuntimeException("HR EMPTY");
        }

        User hrEntity = hrOpt.get();
        userEntity.setCreatedBy(hrEntity);
        userEntity.setPassword(passwordEncoder.encode(data.getPassword()));
        userEntity = userRepo.save(userEntity);

        if ((data.getRoles() != null) && !data.getRoles().isEmpty()) {
            userEntity.setRoles(new ArrayList<>());
            for (UUID roleId : data.getRoles()) {
                Optional<Role> roleOpt = roleRepo.findById(roleId);
                if (roleOpt.isEmpty()) {
                    throw new RuntimeException(String.format("Role with id %s could not be found!",
                            roleId.toString()));
                }
                UserRole userRole = new UserRole();
                userRole.setUser(userEntity);
                userRole.setRole(roleOpt.get());
                userEntity.getRoles().add(userRole);
            }

        }

        return new UserDto(userEntity, true, false);
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
        User user = userOpt.get();
        user.getRoles();
        return new UserDto(user, true, true);
    }

}
