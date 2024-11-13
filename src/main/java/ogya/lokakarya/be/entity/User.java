package ogya.lokakarya.be.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_APP_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", length = 32)
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
    private List<AssessmentSummary> assessments;
}
