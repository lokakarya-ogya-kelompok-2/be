package ogya.lokakarya.be.entity;

import java.io.Serializable;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.entity.common.AuditMetadata;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "TBL_EMP_ATTITUDE_SKILL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ATTITUDE_SKILL_ID", "USER_ID", "ASSESSMENT_YEAR"})})
public class EmpAttitudeSkill extends AuditMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ATTITUDE_SKILL_ID", nullable = false)
    private AttitudeSkill attitudeSkill;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

    @Column(name = "ASSESSMENT_YEAR", nullable = false)
    private Integer assessmentYear;
}
