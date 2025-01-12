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
@Table(name = "TBL_EMP_ACHIEVEMENT_SKill", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "ACHIEVEMENT_ID", "ASSESSMENT_YEAR"})})
public class EmpAchievementSkill extends AuditMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "NOTES", length = 100, nullable = false)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "ACHIEVEMENT_ID", nullable = false)
    private Achievement achievement;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

    @Column(name = "ASSESSMENT_YEAR", nullable = false)
    private Integer assessmentYear;
}
