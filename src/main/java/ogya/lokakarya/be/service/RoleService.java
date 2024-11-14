package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.Role.CreateRole;
import ogya.lokakarya.be.dto.Role.RoleDto;
import ogya.lokakarya.be.entity.Role;
import java.util.List;
import java.util.UUID;

public interface RoleService {
    Role create(CreateRole data);
    List<RoleDto> getAllRole();
    RoleDto getRoleById(UUID id);
    RoleDto updateRoleById(UUID id, CreateRole createRole);
    boolean deleteRoleById(UUID id);

}
