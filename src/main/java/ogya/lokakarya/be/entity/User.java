package ogya.lokakarya.be.entity;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_APP_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", length = 36)
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

    @ManyToMany
    @JoinTable(name = "TBL_APP_USER_ROLE", joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private java.util.Date createdAt = new java.util.Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Column(name = "UPDATED_AT")
    @JoinColumn(name = "updated_at")
    private java.util.Date updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UPDATED_BY")
    private User updatedBy;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AssessmentSummary> assessments;
}
