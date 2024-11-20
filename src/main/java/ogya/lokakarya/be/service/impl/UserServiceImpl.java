package ogya.lokakarya.be.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.dto.user.UserReq;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.entity.UserRole;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.DivisionRepository;
import ogya.lokakarya.be.repository.RoleRepository;
import ogya.lokakarya.be.repository.UserRepository;
import ogya.lokakarya.be.repository.UserRoleRepository;
import ogya.lokakarya.be.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRoleRepository userRoleRepo;

    @Autowired
    private DivisionRepository divisionRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    @Transactional
    public UserDto create(UserReq data) {
        User userEntity = data.toEntity();

        User currentUserEntity = securityUtil.getCurrentUser();
        userEntity.setCreatedBy(currentUserEntity);
        userEntity.setPassword(passwordEncoder.encode(data.getPassword()));

        if (data.getDivisionId() != null) {
            Optional<Division> divisionOpt = divisionRepo.findById(data.getDivisionId());
            if (divisionOpt.isEmpty()) {
                throw new RuntimeException(String.format("Division with id %s could not be found!",
                        data.getDivisionId().toString()));
            }
            userEntity.setDivision(divisionOpt.get());
        }

        userEntity = userRepo.save(userEntity);

        if ((data.getRoles() != null) && !data.getRoles().isEmpty()) {
            userEntity.setUserRoles(new ArrayList<>());
            for (UUID roleId : data.getRoles()) {
                Optional<Role> roleOpt = roleRepo.findById(roleId);
                if (roleOpt.isEmpty()) {
                    throw new RuntimeException(String.format("Role with id %s could not be found!",
                            roleId.toString()));
                }
                UserRole userRole = new UserRole();
                userRole.setUser(userEntity);
                userRole.setRole(roleOpt.get());
                userRoleRepo.save(userRole);
                userEntity.getUserRoles().add(userRole);
            }
        }

        return new UserDto(userEntity, true, false, true);
    }

    @Override
    public List<UserDto> list() {
        List<User> userEntities = userRepo.findAll();
        List<UserDto> users = new ArrayList<>(userEntities.size());
        userEntities.forEach(user -> users.add(new UserDto(user, true, true, true)));
        return users;
    }

    @Override
    public UserDto get(UUID id) {
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            throw ResponseException.userNotFound("Get User By ID", id);
        }
        User user = userOpt.get();
        return new UserDto(user, true, true, true);
    }

    @Transactional
    @Override
    public UserDto update(UUID id, UserReq data) {
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("USER WITH GIVEN ID COULD NOT BE FOUND!");
        }
        User user = userOpt.get();
        User currentUser = securityUtil.getCurrentUser();
        if (data.getUsername() != null) {
            user.setUsername(data.getUsername());
        }
        if (data.getFullName() != null) {
            user.setFullName(data.getFullName());
        }
        if (data.getPosition() != null) {
            user.setPosition(data.getPosition());
        }
        if (data.getEmployeeStatus() != null) {
            user.setEmployeeStatus(data.getEmployeeStatus());
        }
        if (data.getEmail() != null) {
            user.setEmailAddress(data.getEmail());
        }
        if (data.getJoinDate() != null) {
            user.setJoinDate(Date.valueOf(data.getJoinDate()));
        }
        if (data.getPassword() != null) {
            System.out.println(passwordEncoder.matches(data.getPassword(), user.getPassword())
                    + " REQ AND ENTITY");
            String encodedPassword = passwordEncoder.encode(data.getPassword());
            System.out.println(passwordEncoder.matches(data.getPassword(), encodedPassword)
                    + " REQ AND REQ ENCODED");
            System.out.println(
                    encodedPassword + "<< FROM REQ || FROM ENTITY >>" + user.getPassword());
            user.setPassword(encodedPassword);
        }

        if (data.getDivisionId() != null) {
            Optional<Division> divisionOpt = divisionRepo.findById(data.getDivisionId());
            if (divisionOpt.isEmpty()) {
                throw new RuntimeException("DIVISION WITH GIVEN ID COULD NOT BE FOUND!");
            }
            user.setDivision(divisionOpt.get());
        }

        user.setUpdatedBy(currentUser);

        user = userRepo.save(user);

        userRoleRepo.deleteByUserId(id);
        entityManager.flush();

        if ((data.getRoles() != null) && !data.getRoles().isEmpty()) {
            user.setUserRoles(new ArrayList<>());
            for (UUID roleId : data.getRoles()) {
                Optional<Role> roleOpt = roleRepo.findById(roleId);
                if (roleOpt.isEmpty()) {
                    throw new RuntimeException(String.format("Role with id %s could not be found!",
                            roleId.toString()));
                }
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(roleOpt.get());
                userRoleRepo.save(userRole);
                user.getUserRoles().add(userRole);
            }
        }

        return new UserDto(user, true, true, true);
    }
}
