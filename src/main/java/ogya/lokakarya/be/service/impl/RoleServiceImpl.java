package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.dto.role.RoleReq;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.RoleRepository;
import ogya.lokakarya.be.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Transactional
    @Override
    public RoleDto create(RoleReq data) {
        User currentUser = securityUtil.getCurrentUser();
        Role roleEntity = data.toEntity();
        roleEntity.setCreatedBy(currentUser);
        roleEntity = roleRepository.save(roleEntity);
        return new RoleDto(roleEntity, true, true, false);
    }

    @Override

    public List<RoleDto> getAllRole() {
        List<RoleDto> listResult = new ArrayList<>();
        List<Role> roleList = roleRepository.findAll();
        for (Role role : roleList) {
            RoleDto result = convertToDto(role);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public RoleDto getRoleById(UUID id) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isEmpty()) {
            throw ResponseException.roleNotFound(id);
        }
        Role data = roleOpt.get();
        return convertToDto(data);
    }

    @Override
    public RoleDto updateRoleById(UUID id, RoleReq createRole) {
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
        return convertToDto(role);
    }

    @Override
    public boolean deleteRoleById(UUID id) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isEmpty()) {
            throw ResponseException.roleNotFound(id);
        }
        roleRepository.delete(roleOpt.get());
        return true;
    }


    private RoleDto convertToDto(Role data) {
        return new RoleDto(data, true, true, true);
    }
}
