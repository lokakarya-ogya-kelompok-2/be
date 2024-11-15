package ogya.lokakarya.be.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name="TBL_DEV_PLAN")
public class DevPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID")
    private UUID id;

    @Column(name ="PLAN" , length = 100)
    private String menuName;

    @Column(name = "ENABLED", length = 1)
    private boolean enabled=true;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;
//
//    @OneToMany(mappedBy = "dev_plan", fetch = FetchType.LAZY)
//    private List<EmpDevPlan> empDevPlans;
}
