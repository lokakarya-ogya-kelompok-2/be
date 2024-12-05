package ogya.lokakarya.be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "TBL_EMP_DEV_PLAN")
public class EmpDevPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "DEV_PLAN_ID")
    private DevPlan devPlan;

    @JoinColumn(name = "TOO_BRIGHT")
    private String tooBright;

    @Column(name = "ASSESSMENT_YEAR", nullable = false, length = 4)
    private Integer assessmentYear;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = new Date();

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private User updatedBy;
}
