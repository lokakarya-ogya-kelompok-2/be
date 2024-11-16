package ogya.lokakarya.be.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="TBL_ACHIEVEMENT")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID")
    private UUID id;

    @Column(name ="ACHIEVEMENT" , length = 100)
    private String achievement;

//    @ManyToOne
//    @JoinColumn(name ="GROUP_ID")
//    private GroupAchievement groupAchievement;

    @Column(name = "ENABLED")
    private Boolean enabled = true;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;

    @OneToMany(mappedBy = "achievement", fetch = FetchType.LAZY)
    private List<EmpAchievementSkill> empAchievementSkills;
}
