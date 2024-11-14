package ogya.lokakarya.be.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="TBL_GROUP_ATTITUDE_SKILL")
public class GroupAttitudeSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID")
    private UUID id;

    @Column(name ="GROUP_NAME" , length = 100)
    private String groupName;

    @Column(name ="PERCENTAGE", nullable = false, length = 3)
    private Integer percentage;

    @Column(name = "ENABLED")
    private Boolean enabled = true;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = Date.valueOf(LocalDate.now());

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;

//    @OneToMany(mappedBy = "attitude_skill", fetch = FetchType.EAGER)
//    private List<AttitudeSkill> attitudeSkill;
}
