package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.role.RoleFilter;
import ogya.lokakarya.be.entity.Role;

public interface RoleRepository
        extends JpaRepository<Role, UUID>, FilterRepository<Role, RoleFilter> {
}
