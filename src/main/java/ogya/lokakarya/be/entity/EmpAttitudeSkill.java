package ogya.lokakarya.be.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name="TBL_EMP_ATTITUDE_SKILL")
public class EmpAttitudeSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "attitude_skill_id")
    private AttitudeSkill attitudeSkill;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "SCORE", nullable = false, length = 3)
    private Integer score;

    @Column(name = "ASSESSMENT_YEAR", nullable = false, length = 4)
    private Integer assessmentYear;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = Date.valueOf(LocalDate.now());

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;
}

