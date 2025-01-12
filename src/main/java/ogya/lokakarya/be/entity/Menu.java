package ogya.lokakarya.be.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.entity.common.AuditMetadata;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "TBL_APP_MENU", uniqueConstraints = {
        @UniqueConstraint(name = "UK_MENU_MENU_NAME", columnNames = {"MENU_NAME"})})
public class Menu extends AuditMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Column(name = "MENU_NAME", length = 30, nullable = false)
    private String menuName;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.REMOVE)
    private List<RoleMenu> roleMenus;
}
