package ogya.lokakarya.be.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryFilter;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryReq;

public interface AssessmentSummaryService {
        AssessmentSummaryDto calculateAssessmentSummary(UUID userId, Integer year);

        AssessmentSummaryDto create(AssessmentSummaryReq data);

        Page<AssessmentSummaryDto> getAllAssessmentSummaries(AssessmentSummaryFilter filter);

        AssessmentSummaryDto getAssessmentSummaryById(UUID id);

        AssessmentSummaryDto updateAssessmentSummaryById(UUID id,
                        AssessmentSummaryReq assessmentSummaryReq);

        boolean deleteAssessmentSummaryById(UUID id);

        void recalculateAllAssessmentSummaries();

        AssessmentSummaryDto calculateAssessmentSummaryButValidateTheUserIdFirstBeforeCalculating(
                        UUID userId, Integer year);
}
