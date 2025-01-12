package ogya.lokakarya.be.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "TBL_APP_ROLE", uniqueConstraints = {
        @UniqueConstraint(name = "UK_ROLE_ROLENAME", columnNames = {"ROLENAME"})})
public class Role extends AuditMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Column(name = "ROLENAME", nullable = false, length = 30)
    private String roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<RoleMenu> roleMenus;

    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private List<UserRole> userRoles;
}
