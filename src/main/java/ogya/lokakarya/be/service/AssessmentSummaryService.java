package ogya.lokakarya.be.service;


import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryReq;

import java.util.List;
import java.util.UUID;

public interface AssessmentSummaryService {
    AssessmentSummaryDto create(AssessmentSummaryReq data);
    List<AssessmentSummaryDto> getAllAssessmentSummaries();
    AssessmentSummaryDto getAssessmentSummaryById(UUID id);
    AssessmentSummaryDto updateAssessmentSummaryById(UUID id, AssessmentSummaryReq assessmentSummaryReq);
    boolean deleteAssessmentSummaryById(UUID id);
}
