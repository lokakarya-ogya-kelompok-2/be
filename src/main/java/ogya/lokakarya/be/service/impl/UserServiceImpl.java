package ogya.lokakarya.be.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.user.UserChangePasswordDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.dto.user.UserReq;
import ogya.lokakarya.be.dto.user.UserUpdateDto;
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
import ogya.lokakarya.be.util.RandGen;

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
                throw ResponseException.divisionNotFound(data.getDivisionId());
            }
            userEntity.setDivision(divisionOpt.get());
        }

        userEntity = userRepo.save(userEntity);

        if ((data.getRoles() != null) && !data.getRoles().isEmpty()) {
            userEntity.setUserRoles(new ArrayList<>());
            for (UUID roleId : data.getRoles()) {
                Optional<Role> roleOpt = roleRepo.findById(roleId);
                if (roleOpt.isEmpty()) {
                    throw ResponseException.roleNotFound(roleId);
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
            throw ResponseException.userNotFound(id);
        }
        User user = userOpt.get();
        return new UserDto(user, true, true, true);
    }

    @SuppressWarnings("java:S3776")
    @Transactional
    @Override
    public UserDto update(UUID id, UserUpdateDto data) {
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            throw ResponseException.userNotFound(id);
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

        if (data.getDivisionId() != null) {
            Optional<Division> divisionOpt = divisionRepo.findById(data.getDivisionId());
            if (divisionOpt.isEmpty()) {
                throw ResponseException.divisionNotFound(data.getDivisionId());
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
                    throw ResponseException.roleNotFound(roleId);
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

    @Transactional
    @Override
    public void delete(UUID id) {
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            throw ResponseException.userNotFound(id);
        }
        User user = userOpt.get();
        userRoleRepo.deleteByUserId(id);
        entityManager.flush();
        userRepo.delete(user);
    }

    @Override
    public UserDto changePassword(UserChangePasswordDto data) {
        User currentUser = securityUtil.getCurrentUser();
        if (!passwordEncoder.matches(data.getCurrentPassword(), currentUser.getPassword())) {
            throw new ResponseException("Incorrect current password!", HttpStatus.BAD_REQUEST);
        }
        if (!data.getNewPassword().equals(data.getConfirmNewPassword())) {
            throw new ResponseException("New password and confirmation password mismatch!",
                    HttpStatus.BAD_REQUEST);
        }
        if (data.getNewPassword().equals(data.getCurrentPassword())) {
            throw new ResponseException("New password can't be the same as current password!",
                    HttpStatus.BAD_REQUEST);
        }

        currentUser.setPassword(passwordEncoder.encode(data.getNewPassword()));
        currentUser = userRepo.save(currentUser);

        return new UserDto(currentUser, true, true, false);
    }

    @Override
    public String resetPassword(UUID userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) {
            throw ResponseException.userNotFound(userId);
        }
        User user = userOpt.get();
        User currentUser = securityUtil.getCurrentUser();
        String randomGenerated = RandGen.generate(10);
        user.setUpdatedBy(currentUser);
        user.setPassword(passwordEncoder.encode(randomGenerated));
        userRepo.save(user);
        return randomGenerated;
    }
}
