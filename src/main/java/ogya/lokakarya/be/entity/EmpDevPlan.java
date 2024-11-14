package ogya.lokakarya.be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name="TBL_EMP_DEV_PLAN")
public class EmpDevPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID", length=32)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dev_plan_id")
    private DevPlan devPlan;

    @Column(name = "ASSESSMENT_YEAR", nullable = false, length = 4)
    private Integer assessmentYear;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = Date.valueOf(LocalDate.now());

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;
}
