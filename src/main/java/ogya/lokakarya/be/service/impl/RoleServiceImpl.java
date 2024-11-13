package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.Role.CreateRole;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.repository.role.RoleRepository;
import ogya.lokakarya.be.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(CreateRole data) {
        return roleRepository.save(data.toEntity());
    }
}
