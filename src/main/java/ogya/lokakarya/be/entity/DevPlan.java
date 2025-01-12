package ogya.lokakarya.be.entity;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.entity.common.AuditMetadata;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "TBL_DEV_PLAN",
        uniqueConstraints = {@UniqueConstraint(name = "UK_DEV_PLAN_NAME", columnNames = {"PLAN"})})
public class DevPlan extends AuditMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Column(name = "PLAN", length = 100, nullable = false)
    private String name;

    @Column(name = "ENABLED")
    private boolean enabled = true;

    @OneToMany(mappedBy = "devPlan", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<EmpDevPlan> empDevPlans;
}
