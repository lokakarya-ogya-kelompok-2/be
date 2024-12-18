package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryFilter;
import ogya.lokakarya.be.entity.AssessmentSummary;

public interface AssessmentSummaryRepository extends JpaRepository<AssessmentSummary, UUID>,
        FilterRepository<AssessmentSummary, AssessmentSummaryFilter> {
    // @Query(value = "SELECT * FROM tbl_assessment_summary WHERE user_id= ? AND year = ? ",
    // nativeQuery = true)
    // Optional<AssessmentSummary> findByUserIdAndYear(UUID userId, Integer year);
}
