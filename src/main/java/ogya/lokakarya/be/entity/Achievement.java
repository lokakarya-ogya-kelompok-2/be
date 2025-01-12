package ogya.lokakarya.be.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.entity.common.AuditMetadata;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "TBL_ACHIEVEMENT",
                uniqueConstraints = {@UniqueConstraint(name = "UK_ACHIEVEMENT_NAME_GROUP_ID",
                                columnNames = {"ACHIEVEMENT", "GROUP_ID"})})
public class Achievement extends AuditMetadata implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "ID", nullable = false)
        private UUID id;

        @Column(name = "ACHIEVEMENT", length = 100, nullable = false)
        private String name;

        @ManyToOne
        @JoinColumn(name = "GROUP_ID", nullable = false,
                        foreignKey = @ForeignKey(name = "FK_ACHIEVEMENT_GROUP_ACHIEVEMENT"))
        private GroupAchievement groupAchievement;

        @Column(name = "ENABLED")
        private Boolean enabled = true;

        @OneToMany(mappedBy = "achievement", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
        private List<EmpAchievementSkill> empAchievementSkills;
}
