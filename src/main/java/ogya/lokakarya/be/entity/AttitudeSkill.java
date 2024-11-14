package ogya.lokakarya.be.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="TBL_ATTITUDE_SKILL")
public class AttitudeSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID", length=32)
    private UUID id;

    @Column(name ="ATTITUDE_SKILL", nullable = false , length = 100)
    private String attitudeSkill;

    @ManyToOne
    @JoinColumn(name ="GROUP_ID")
    private GroupAttitudeSkill groupAttitudeSkill;

    @Column(name = "ENABLED")
    private Boolean enabled = false;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = Date.valueOf(LocalDate.now());

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;

    @OneToMany(mappedBy = "emp_attitude_skill", fetch = FetchType.EAGER)
    private List<AttitudeSkill> attitudeSkills;

}
