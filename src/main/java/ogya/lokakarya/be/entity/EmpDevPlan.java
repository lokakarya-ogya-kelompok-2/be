package ogya.lokakarya.be.entity;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.entity.common.AuditMetadata;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "TBL_EMP_DEV_PLAN")
public class EmpDevPlan extends AuditMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "DEV_PLAN_ID", nullable = false)
    private DevPlan devPlan;

    @JoinColumn(name = "PLAN", nullable = false)
    private String plan;

    @Column(name = "ASSESSMENT_YEAR", nullable = false)
    private Integer assessmentYear;
}
