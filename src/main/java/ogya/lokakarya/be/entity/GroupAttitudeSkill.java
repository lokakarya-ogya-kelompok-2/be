package ogya.lokakarya.be.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_GROUP_ATTITUDE_SKILL",
        uniqueConstraints = {@UniqueConstraint(name = "UK_GROUP_ATTITUDE_SKILL_GROUP_NAME",
                columnNames = {"GROUP_NAME"})})
public class GroupAttitudeSkill {
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

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private Date createdAt = new Date();

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private User updatedBy;

    @OneToMany(mappedBy = "groupAttitudeSkill", fetch = FetchType.LAZY)
    private List<AttitudeSkill> attitudeSkills;

    @PreUpdate
    private void fillUpdatedAt() {
        updatedAt = new Date();
    }
}
