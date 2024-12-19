package ogya.lokakarya.be.entity;

import java.util.Date;
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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name = "TBL_EMP_ACHIEVEMENT_SKill", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "USER_ID", "ACHIEVEMENT_ID", "ASSESSMENT_YEAR" })
})
public class EmpAchievementSkill {
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

    @PreUpdate
    private void fillUpdatedAt() {
        updatedAt = new Date();
    }
}
