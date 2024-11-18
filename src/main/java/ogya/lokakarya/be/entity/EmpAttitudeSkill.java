package ogya.lokakarya.be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
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
    private Date createdAt = new Date();

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;
}

