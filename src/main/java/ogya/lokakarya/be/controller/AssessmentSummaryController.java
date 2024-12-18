package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryFilter;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryReq;
import ogya.lokakarya.be.service.AssessmentSummaryService;


@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/assessment-summaries")
public class AssessmentSummaryController {
        @Autowired
        private AssessmentSummaryService assessmentSummaryService;

        @PostMapping
        public ResponseEntity<ResponseDto<AssessmentSummaryDto>> createAssessmentSummary(
                        @RequestBody @Valid AssessmentSummaryReq data) {
                var createdAssessmentSummary = assessmentSummaryService.create(data);
                return ResponseDto.<AssessmentSummaryDto>builder().success(true)
                                .content(createdAssessmentSummary)
                                .message("Create assessment summary successful!").build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<AssessmentSummaryDto>>> getAllAssessmentSummaries(
                        @RequestParam(name = "user_ids", required = false) List<UUID> userIds,
                        @RequestParam(required = false) List<Integer> years,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy) {
                AssessmentSummaryFilter filter = new AssessmentSummaryFilter();
                filter.setUserIds(userIds);
                filter.setYears(years);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                List<AssessmentSummaryDto> assessmentSummaries =
                                assessmentSummaryService.getAllAssessmentSummaries(filter);
                return ResponseDto.<List<AssessmentSummaryDto>>builder().success(true)
                                .content(assessmentSummaries)
                                .message("List all assessment summary successful!").build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<AssessmentSummaryDto>> getAssessmentSummaryById(
                        @PathVariable UUID id) {
                AssessmentSummaryDto assessmentSummary =
                                assessmentSummaryService.getAssessmentSummaryById(id);
                return ResponseDto.<AssessmentSummaryDto>builder().success(true)
                                .content(assessmentSummary)
                                .message(String.format(
                                                "Get assessment summary with id %s successful!",
                                                id))
                                .build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<AssessmentSummaryDto>> updateAssessmentSummaryById(
                        @PathVariable UUID id,
                        @RequestBody @Valid AssessmentSummaryReq assessmentSummaryReq) {
                AssessmentSummaryDto updatedAssessmentSummary = assessmentSummaryService
                                .updateAssessmentSummaryById(id, assessmentSummaryReq);
                return ResponseDto.<AssessmentSummaryDto>builder().success(true)
                                .content(updatedAssessmentSummary)
                                .message(String.format(
                                                "Update assessment summary with id %s successful!",
                                                id))
                                .build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteAssessmentSummaryById(
                        @PathVariable UUID id) {
                assessmentSummaryService.deleteAssessmentSummaryById(id);
                return ResponseDto.<Void>builder().success(true).message(String
                                .format("Delete assessment summary with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }

        @GetMapping("/calculate/{userId}/{year}")
        public ResponseEntity<ResponseDto<AssessmentSummaryDto>> calculateAssessmentSummary(
                        @PathVariable UUID userId, @PathVariable Integer year) {
                var assessmentSummary = assessmentSummaryService
                                .calculateAssessmentSummaryButValidateTheUserIdFirstBeforeCalculating(
                                                userId, year);
                return ResponseDto.<AssessmentSummaryDto>builder()
                                .success(assessmentSummary.getId() != null)
                                .message(String.format(
                                                "Calculate assessment summary for user_id %s and year %d %s!",
                                                userId, year,
                                                assessmentSummary.getId() == null ? "failed"
                                                                : "successful"))
                                .content(assessmentSummary).build().toResponse(HttpStatus.OK);
        }

}
