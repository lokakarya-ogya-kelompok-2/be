package ogya.lokakarya.be.repository.assessmentSummary;

import ogya.lokakarya.be.entity.AssessmentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AssessmentSummaryRepository extends JpaRepository<AssessmentSummary, UUID> {
}
