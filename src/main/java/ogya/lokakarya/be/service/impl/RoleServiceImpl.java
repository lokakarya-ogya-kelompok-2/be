package ogya.lokakarya.be.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.dto.Role.CreateRole;
import ogya.lokakarya.be.entity.Role;
import ogya.lokakarya.be.repository.role.RoleRepository;
import ogya.lokakarya.be.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(CreateRole data) {
        return roleRepository.save(data.toEntity());
    }

    @Override
    public Role findByid(UUID id) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isEmpty()) {
            throw new RuntimeException("ROLE COULD NOT BE FOUND");
        }
        return roleOpt.get();
    }
}
