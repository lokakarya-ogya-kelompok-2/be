package ogya.lokakarya.be.service;


import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryReq;

public interface AssessmentSummaryService {
    AssessmentSummaryDto calculateAssessmentSummary(UUID userId, Integer year);

    AssessmentSummaryDto create(AssessmentSummaryReq data);

    List<AssessmentSummaryDto> getAllAssessmentSummaries();

    AssessmentSummaryDto getAssessmentSummaryById(UUID id);

    AssessmentSummaryDto updateAssessmentSummaryById(UUID id,
            AssessmentSummaryReq assessmentSummaryReq);

    boolean deleteAssessmentSummaryById(UUID id);
}
