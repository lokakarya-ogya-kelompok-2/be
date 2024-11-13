package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.Role.CreateRole;
import ogya.lokakarya.be.entity.Role;

public interface RoleService {
    Role create(CreateRole data);


}
