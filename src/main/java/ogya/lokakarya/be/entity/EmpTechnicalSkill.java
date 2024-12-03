package ogya.lokakarya.be.entity;

import java.util.Date;
import java.util.UUID;
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

@Entity
@Data
@Table(name = "TBL_EMP_TECHNICAL_SKILL")
public class EmpTechnicalSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TECHNICAL_SKILL_ID")
    private TechnicalSkill technicalSkill;

    @Column(name = "SCORE", nullable = false, length = 3)
    private Integer score;

    @Column(name = "DETAIL")
    private String technicalSkilLDetail;

    @Column(name = "ASSESSMENT_YEAR", nullable = false, length = 4)
    private Integer assessmentYear;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private User updatedBy;
}
