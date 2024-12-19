package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.role.RoleDto;
import ogya.lokakarya.be.dto.role.RoleFilter;
import ogya.lokakarya.be.dto.role.RoleReq;

public interface RoleService {
    RoleDto create(RoleReq data);

    List<RoleDto> getAllRole(RoleFilter filter);

    RoleDto getRoleById(UUID id);

    RoleDto updateRoleById(UUID id, RoleReq createRole);

    boolean deleteRoleById(UUID id);

}
