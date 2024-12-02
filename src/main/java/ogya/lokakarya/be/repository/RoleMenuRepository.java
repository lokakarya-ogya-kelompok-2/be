package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.entity.RoleMenu;

public interface RoleMenuRepository extends JpaRepository<RoleMenu, UUID> {
}
