package ogya.lokakarya.be.entity;

import java.io.Serializable;
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
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.entity.common.AuditMetadata;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "TBL_GROUP_ATTITUDE_SKILL",
        uniqueConstraints = {@UniqueConstraint(name = "UK_GROUP_ATTITUDE_SKILL_GROUP_NAME",
                columnNames = {"GROUP_NAME"})})
public class GroupAttitudeSkill extends AuditMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Column(name = "GROUP_NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "PERCENTAGE", nullable = false)
    private Integer weight;

    @Column(name = "ENABLED")
    private Boolean enabled = true;

    @OneToMany(mappedBy = "groupAttitudeSkill", fetch = FetchType.LAZY)
    private List<AttitudeSkill> attitudeSkills;
}
