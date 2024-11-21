package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.dto.role.RoleReq;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    RoleDto create(RoleReq data);
    List<RoleDto> getAllRole();
    RoleDto getRoleById(UUID id);
    RoleDto updateRoleById(UUID id, RoleReq createRole);
    boolean deleteRoleById(UUID id);

}
