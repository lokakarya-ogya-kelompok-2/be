package ogya.lokakarya.be.service;


import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.CreateAssessmentSummary;
import ogya.lokakarya.be.entity.AssessmentSummary;

import java.util.List;
import java.util.UUID;

public interface AssessmentSummaryService {
    AssessmentSummary create(CreateAssessmentSummary data);
    List<AssessmentSummaryDto> getAllAssessmentSummaries();
    AssessmentSummaryDto getAssessmentSummaryById(UUID id);
    AssessmentSummaryDto updateAssessmentSummaryById(UUID id, CreateAssessmentSummary createAssessmentSummary);
    boolean deleteAssessmentSummaryById(UUID id);
}
