package ogya.lokakarya.be.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ogya.lokakarya.be.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    @Query(value = "SELECT\r\n" + //
            "    m.id,\r\n" + //
            "    m.menu_name\r\n" + //
            "FROM\r\n" + //
            "    tbl_app_role r\r\n" + //
            "        LEFT JOIN tbl_app_role_menu rm ON r.id = rm.role_id\r\n" + //
            "        JOIN tbl_app_menu m ON rm.menu_id = m.id\r\n" + //
            "        JOIN tbl_app_user_role ur ON r.id = ur.role_id\r\n" + //
            "        JOIN tbl_app_user u ON ur.user_id = u.id\r\n" + //
            "WHERE\r\n" + //
            "    u.id = ?", nativeQuery = true)
    List<Menu> findByUserId(UUID userId);
}
