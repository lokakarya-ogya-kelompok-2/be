package ogya.lokakarya.be.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.user.UserChangePasswordDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.dto.user.UserFilter;
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
import ogya.lokakarya.be.repository.specification.UserSpecification;
import ogya.lokakarya.be.service.UserService;
import ogya.lokakarya.be.util.RandGen;

@Slf4j
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

    @Autowired
    private UserSpecification spec;

    @Override
    @Transactional
    public UserDto create(UserReq data) {
        log.info("Starting UserServiceImpl.create");
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
                userEntity.getUserRoles().add(userRole);
            }
        }

        userEntity = userRepo.save(userEntity);

        if (userEntity.getUserRoles() != null) {
            for (UserRole userRole : userEntity.getUserRoles()) {
                userRoleRepo.save(userRole);
            }
        }

        log.info("Ending UserServiceImpl.create");
        return new UserDto(userEntity, true, false, true);
    }

    @SuppressWarnings("java:S3776")
    @Override
    public Page<UserDto> list(UserFilter filter) {
        log.info("Starting UserServiceImpl.list");

        filter.validate();
        Specification<User> specification = Specification.where(null);
        if (filter.getAnyStringFieldsContains() != null
                && !filter.getAnyStringFieldsContains().isEmpty()) {
            specification = specification.and(
                    Specification.anyOf(spec.usernameContains(filter.getAnyStringFieldsContains()),
                            spec.fullNameContains(filter.getAnyStringFieldsContains()),
                            spec.positionContains(filter.getAnyStringFieldsContains()),
                            spec.emailContains(filter.getAnyStringFieldsContains()),
                            spec.divisionNameContains(filter.getAnyStringFieldsContains())));
        } else {
            if (filter.getUsernameContains() != null && !filter.getUsernameContains().isEmpty()) {
                specification =
                        specification.and(spec.usernameContains(filter.getUsernameContains()));
            }
            if (filter.getNameContains() != null && !filter.getNameContains().isEmpty()) {
                specification = specification.and(spec.fullNameContains(filter.getNameContains()));
            }
            if (filter.getPositionContains() != null && !filter.getPositionContains().isEmpty()) {
                specification =
                        specification.and(spec.positionContains(filter.getPositionContains()));
            }
            if (filter.getEmailContains() != null && !filter.getEmailContains().isEmpty()) {
                specification = specification.and(spec.emailContains(filter.getEmailContains()));
            }
            if (filter.getDivisionNameContains() != null
                    && !filter.getDivisionNameContains().isEmpty()) {
                specification = specification
                        .and(spec.divisionNameContains(filter.getDivisionNameContains()));
            }
        }
        if (filter.getMinJoinDate() != null && filter.getMaxJoinDate() != null) {
            specification =
                    specification.and(spec.joinDateBetween(Date.valueOf(filter.getMinJoinDate()),
                            Date.valueOf(filter.getMaxJoinDate())));
        } else if (filter.getMinJoinDate() != null) {
            specification =
                    specification.and(spec.joinDateGte(Date.valueOf(filter.getMinJoinDate())));
        } else if (filter.getMaxJoinDate() != null) {
            specification =
                    specification.and(spec.joinDateLte(Date.valueOf(filter.getMaxJoinDate())));
        }
        if (filter.getEmployeeStatus() != null) {
            specification =
                    specification.and(spec.employeeStatusEquals(filter.getEmployeeStatus()));
        }
        if (filter.getEnabledOnly().booleanValue()) {
            specification = specification.and(spec.enabledEquals(true));
        }

        Sort sortBy = Sort.by(filter.getSortDirection(), filter.getSortField());

        Page<User> users;
        if (filter.getPageNumber() != null) {
            Pageable pageable = PageRequest.of(Math.max(0, filter.getPageNumber() - 1),
                    Math.max(1, filter.getPageSize()), sortBy);
            users = userRepo.findAll(specification, pageable);
        } else {
            users = new PageImpl<>(userRepo.findAll(specification, sortBy));
        }

        log.info("Ending UserServiceImpl.list");
        return users.map(user -> new UserDto(user, filter.getWithCreatedBy(),
                filter.getWithUpdatedBy(), filter.getWithRoles()));
    }

    @Override
    public UserDto get(UUID id) {
        log.info("Starting UserServiceImpl.get");
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            throw ResponseException.userNotFound(id);
        }
        User user = userOpt.get();
        log.info("Ending UserServiceImpl.get");
        return new UserDto(user, true, true, true);
    }

    @SuppressWarnings("java:S3776")
    @Transactional
    @Override
    public UserDto update(UUID id, UserUpdateDto data) {
        log.info("Starting UserServiceImpl.update");
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
        if (data.getEnabled() != null) {
            user.setEnabled(data.getEnabled());
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
        log.info("Ending UserServiceImpl.update");
        return new UserDto(user, true, true, true);
    }

    @Override
    public void delete(UUID id) {
        log.info("Starting UserServiceImpl.delete");
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            throw ResponseException.userNotFound(id);
        }
        User user = userOpt.get();
        userRepo.delete(user);
        log.info("Ending UserServiceImpl.delete");
    }

    @Override
    public UserDto changePassword(UserChangePasswordDto data) {
        log.info("Starting UserServiceImpl.changePassword");
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
        currentUser.setPasswordRecentlyReset(false);
        currentUser.setUpdatedBy(currentUser);
        currentUser = userRepo.save(currentUser);
        log.info("Ending UserServiceImpl.changePassword");
        return new UserDto(currentUser, true, true, false);
    }

    @Override
    public String resetPassword(UUID userId) {
        log.info("Starting UserServiceImpl.resetPassword");
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) {
            throw ResponseException.userNotFound(userId);
        }
        User user = userOpt.get();
        User currentUser = securityUtil.getCurrentUser();
        String randomGenerated = RandGen.generate(10);
        user.setUpdatedBy(currentUser);
        user.setPassword(passwordEncoder.encode(randomGenerated));
        user.setPasswordRecentlyReset(true);
        userRepo.save(user);
        log.info("Ending UserServiceImpl.resetPassword");
        return randomGenerated;
    }
}
