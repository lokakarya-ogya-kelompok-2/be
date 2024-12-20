package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ogya.lokakarya.be.entity.AssessmentSummary;

public interface AssessmentSummaryRepository extends JpaRepository<AssessmentSummary, UUID>,
        JpaSpecificationExecutor<AssessmentSummary> {
}
