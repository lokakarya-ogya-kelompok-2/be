package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.role.RoleReq;
import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.entity.Role;
import java.util.List;
import java.util.UUID;

public interface RoleService {
    Role create(RoleReq data);
    List<RoleDto> getAllRole();
    RoleDto getRoleById(UUID id);
    RoleDto updateRoleById(UUID id, RoleReq createRole);
    boolean deleteRoleById(UUID id);

}
