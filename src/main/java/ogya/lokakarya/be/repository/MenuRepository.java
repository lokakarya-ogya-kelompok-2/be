package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.menu.MenuFilter;
import ogya.lokakarya.be.entity.Menu;

public interface MenuRepository
        extends JpaRepository<Menu, UUID>, FilterRepository<Menu, MenuFilter> {
}
