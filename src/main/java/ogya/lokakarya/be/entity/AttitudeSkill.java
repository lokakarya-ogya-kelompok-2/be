package ogya.lokakarya.be.entity;

import java.io.Serializable;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
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
@Table(name = "TBL_ATTITUDE_SKILL",
        uniqueConstraints = {@UniqueConstraint(name = "UK_ATTITUDE_SKILL_GROUP_NAME",
                columnNames = {"ATTITUDE_SKILL", "GROUP_ID"})})
public class AttitudeSkill extends AuditMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Column(name = "ATTITUDE_SKILL", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_ATTITUDE_SKILL_GROUP_ATTITUDE_SKILL"))
    private GroupAttitudeSkill groupAttitudeSkill;

    @Column(name = "ENABLED")
    private Boolean enabled = true;
}
