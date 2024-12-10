package ogya.lokakarya.be.entity;

import java.util.Date;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "TBL_ASSESSMENT_SUMMARY",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "YEAR"})})
public class AssessmentSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "YEAR", nullable = false)
    private Integer year;

    @Column(name = "SCORE", nullable = false, length = 3)
    private Integer score;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private UUID updatedBy;
}
