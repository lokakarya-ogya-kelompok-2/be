package ogya.lokakarya.be.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_APP_USER")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "USERNAME", unique = true, nullable = false, length = 30)
    private String username;

    @Column(name = "FULL_NAME", nullable = false, length = 60)
    private String fullName;

    @Column(name = "POSITION", nullable = false, length = 50)
    private String position;

    @Column(name = "EMAIL_ADDRESS", unique = true, nullable = false, length = 60)
    private String emailAddress;

    @Column(name = "EMPLOYEE_STATUS", nullable = false, length = 1)
    private Integer employeeStatus;

    @Column(name = "JOIN_DATE", nullable = false)
    private Date joinDate;

    @Column(name = "ENABLED", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean enabled = true;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private java.util.Date createdAt = new java.util.Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Column(name = "UPDATED_AT")
    @JoinColumn(name = "updated_at")
    private java.util.Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private User updatedBy;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AssessmentSummary> assessmentSummary;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EmpAttitudeSkill> empAttitudeSkills;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EmpAchievementSkill> empAchievementSkills;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EmpSuggestion> empSuggestions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DIVISION_ID", nullable = false)
    private Division division;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        userRoles.forEach(userRole -> roles
                .add(new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getRoleName())));
        return roles;
    }


    @PreUpdate
    private void fillUpdatedAt() {
        updatedAt = new java.util.Date();
    }
}
