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
@Table(name = "TBL_TECHNICAL_SKILL", uniqueConstraints = {
        @UniqueConstraint(name = "UK_TECHNICAL_SKILL_NAME", columnNames = "TECHNICAL_SKILL")})
public class TechnicalSkill extends AuditMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Column(name = "TECHNICAL_SKILL", length = 100, nullable = false)
    private String name;

    @Column(name = "ENABLED")
    private Boolean enabled = true;

    @OneToMany(mappedBy = "technicalSkill", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<EmpTechnicalSkill> empTechnicalSkills;
}
