package ogya.lokakarya.be.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_APP_ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "ROLENAME", unique = true, nullable = false, length = 30)
    private String roleName;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date createdAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private User updatedBy;

    // @ManyToMany
    // @JoinTable(name = "TBL_APP_ROLE_MENU", joinColumns = @JoinColumn(name = "ROLE_ID"),
    // inverseJoinColumns = @JoinColumn(name = "MENU_ID"))
    // private Set<RoleMenu> menus;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<RoleMenu> roleMenus;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;
}
