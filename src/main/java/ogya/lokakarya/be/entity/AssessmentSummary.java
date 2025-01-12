package ogya.lokakarya.be.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.entity.common.AuditMetadata;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "TBL_ASSESSMENT_SUMMARY",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "YEAR"},
                name = "UK_ASSESSMENT_SUMMARY_USER_YEAR")})
public class AssessmentSummary extends AuditMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "YEAR", nullable = false)
    private Integer year;

    @Column(name = "SCORE", nullable = false, precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "APPROVAL_STATUS", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer approvalStatus = 0;

    @Column(name = "APPROVAL_DATE")
    private Date approvedAt;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVER_ID")
    private User approver;
}
