package ogya.lokakarya.be.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.dto.role.RoleFilter;
import ogya.lokakarya.be.dto.role.RoleReq;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.RoleRepository;
import ogya.lokakarya.be.service.RoleService;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Transactional
    @Override
    public RoleDto create(RoleReq data) {
        log.info("Starting RoleServiceImpl.create");
        User currentUser = securityUtil.getCurrentUser();
        Role roleEntity = data.toEntity();
        roleEntity.setCreatedBy(currentUser);
        roleEntity = roleRepository.save(roleEntity);
        log.info("Ending RoleServiceImpl.create");
        return new RoleDto(roleEntity, true, false, false);
    }

    @Override
    public List<RoleDto> getAllRole(RoleFilter filter) {
        log.info("Starting RoleServiceImpl.getAllRole");
        List<Role> roles = roleRepository.findAllByFilter(filter);
        log.info("Ending RoleServiceImpl.getAllRole");
        return roles.stream().map(role -> new RoleDto(role, filter.getWithCreatedBy(),
                filter.getWithUpdatedBy(), filter.getWithMenus())).toList();
    }

    @Override
    public RoleDto getRoleById(UUID id) {
        log.info("Starting RoleServiceImpl.getRoleById");
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isEmpty()) {
            throw ResponseException.roleNotFound(id);
        }
        Role data = roleOpt.get();
        log.info("Ending RoleServiceImpl.getRoleById");
        return convertToDto(data);
    }

    @Override
    public RoleDto updateRoleById(UUID id, RoleReq createRole) {
        log.info("Starting RoleServiceImpl.updateRoleById");
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isEmpty()) {
            throw ResponseException.roleNotFound(id);
        }
        Role role = roleOpt.get();
        User currentUser = securityUtil.getCurrentUser();
        if (createRole.getRoleName() != null) {
            role.setRoleName(createRole.getRoleName());
        }
        role.setUpdatedBy(currentUser);
        role = roleRepository.save(role);
        log.info("Ending RoleServiceImpl.updateRoleById");
        return convertToDto(role);
    }

    @Override
    public boolean deleteRoleById(UUID id) {
        log.info("Starting RoleServiceImpl.deleteRoleById");
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isEmpty()) {
            throw ResponseException.roleNotFound(id);
        }
        roleRepository.delete(roleOpt.get());
        log.info("Ending RoleServiceImpl.deleteRoleById");
        return true;
    }


    private RoleDto convertToDto(Role data) {
        return new RoleDto(data, true, true, true);
    }
}
