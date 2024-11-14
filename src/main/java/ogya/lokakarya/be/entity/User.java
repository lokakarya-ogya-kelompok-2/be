package ogya.lokakarya.be.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_APP_USER")
public class User {
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

    @Column(name = "JOIN_DATE", nullable = false)
    private Date joinDate;

    @Column(name = "ENABLED")
    private Boolean enabled = false;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = Date.valueOf(LocalDate.now());

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AssessmentSummary> assessmentSummary;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EmpAttitudeSkill> empAttitudeSkills;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EmpAchievementSkill> empAchievementSkills;

    @ManyToMany
    @JoinTable(
            name = "TBL_ACCESS_DIVISION",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "DIVISION_ID")
    )
    private Set<Division> divisions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "TBL_APP_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Role> roles = new HashSet<>();
}
