package ogya.lokakarya.be.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name="TBL_TECHNICAL_SKILL")
public class TechnicalSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID")
    private UUID id;

    @Column(name ="TECHNICAL_SKILL" , length = 100)
    private String technicalSkill;

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

//    @OneToMany(mappedBy = "technical_skill", fetch = FetchType.LAZY)
//    private List<> empAchievementSkills;
}
