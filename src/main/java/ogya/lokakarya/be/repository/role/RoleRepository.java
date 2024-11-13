package ogya.lokakarya.be.repository.role;

import ogya.lokakarya.be.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
