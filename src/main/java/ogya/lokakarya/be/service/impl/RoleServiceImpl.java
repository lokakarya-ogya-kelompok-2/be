package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.dto.role.RoleReq;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.entity.User;
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
        Optional<Role> listData;
        try {
            listData = roleRepository.findById(id);
            System.out.println(listData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        Role data = listData.get();
        return convertToDto(data);
    }

    @Override
    public RoleDto updateRoleById(UUID id, RoleReq createRole) {
        Optional<Role> listData = roleRepository.findById(id);
        if (listData.isPresent()) {
            Role role = listData.get();
            if (!createRole.getRoleName().isBlank()) {
                role.setRoleName(createRole.getRoleName());
            }
            roleRepository.save(role);
            RoleDto roleDto = convertToDto(role);
            return roleDto;
        }
        return null;
    }

    @Override
    public boolean deleteRoleById(UUID id) {
        Optional<Role> listData = roleRepository.findById(id);
        if (listData.isPresent()) {
            roleRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }


    private RoleDto convertToDto(Role data) {
        RoleDto result = new RoleDto(data, true, true, true);
        return result;
    }
}
