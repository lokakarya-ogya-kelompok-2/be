package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
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
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryFilter;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryReq;
import ogya.lokakarya.be.service.AssessmentSummaryService;


@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/assessment-summaries")
public class AssessmentSummaryController {
        @Autowired
        private AssessmentSummaryService assessmentSummaryService;

        @PostMapping
        public ResponseEntity<ResponseDto<AssessmentSummaryDto>> createAssessmentSummary(
                        @RequestBody @Valid AssessmentSummaryReq data) {
                log.info("Starting AssessmentSummaryController.create");
                var createdAssessmentSummary = assessmentSummaryService.create(data);
                log.info("Ending AssessmentSummaryController.create");
                return ResponseDto.<AssessmentSummaryDto>builder().success(true)
                                .content(createdAssessmentSummary)
                                .message("Create assessment summary successful!").build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<AssessmentSummaryDto>>> getAllAssessmentSummaries(
                        @RequestParam(name = "sort_field", required = false,
                                        defaultValue = "createdAt") String sortField,
                        @RequestParam(name = "sort_direction", required = false,
                                        defaultValue = "DESC") Direction sortDirection,
                        @RequestParam(name = "any_contains",
                                        required = false) String anyStringFieldContains,
                        @RequestParam(name = "user_ids", required = false) List<UUID> userIds,
                        @RequestParam(name = "division_ids",
                                        required = false) List<UUID> divisionIds,
                        @RequestParam(required = false) List<Integer> years,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy,
                        @RequestParam(name = "page_number", required = false) Integer pageNumber,
                        @RequestParam(name = "page_size", required = false,
                                        defaultValue = "5") Integer pageSize) {
                log.info("Starting AssessmentSummaryController.list");
                AssessmentSummaryFilter filter = new AssessmentSummaryFilter();
                filter.setUserIds(userIds);
                filter.setAnyStringFieldContains(anyStringFieldContains);
                filter.setDivisionIds(divisionIds);
                filter.setYears(years);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                filter.setPageSize(pageSize);
                filter.setPageNumber(pageNumber);
                filter.setSortField(sortField);
                filter.setSortDirection(sortDirection);

                Page<AssessmentSummaryDto> assessmentSummaries =
                                assessmentSummaryService.getAllAssessmentSummaries(filter);
                log.info("Ending AssessmentSummaryController.list");
                return ResponseDto.<List<AssessmentSummaryDto>>builder().success(true)
                                .content(assessmentSummaries.toList())
                                .totalRecords(assessmentSummaries.getTotalElements())
                                .totalPages(assessmentSummaries.getTotalPages())
                                .pageNumber(assessmentSummaries.getNumber() + 1)
                                .pageSize(assessmentSummaries.getSize())
                                .message("List all assessment summary successful!").build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<AssessmentSummaryDto>> getAssessmentSummaryById(
                        @PathVariable UUID id) {
                log.info("Starting AssessmentSummaryController.get for id = {}", id);
                AssessmentSummaryDto assessmentSummary =
                                assessmentSummaryService.getAssessmentSummaryById(id);
                log.info("Ending AssessmentSummaryController.get for id = {}", id);
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
                log.info("Starting AssessmentSummaryController.update for id = {}", id);
                AssessmentSummaryDto updatedAssessmentSummary = assessmentSummaryService
                                .updateAssessmentSummaryById(id, assessmentSummaryReq);
                log.info("Ending AssessmentSummaryController.update for id = {}", id);
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
                log.info("Starting AssessmentSummaryController.delete for id = {}", id);
                assessmentSummaryService.deleteAssessmentSummaryById(id);
                log.info("Ending AssessmentSummaryController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true).message(String
                                .format("Delete assessment summary with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }

        @GetMapping("/calculate/{userId}/{year}")
        public ResponseEntity<ResponseDto<AssessmentSummaryDto>> calculateAssessmentSummary(
                        @PathVariable UUID userId, @PathVariable Integer year) {
                log.info("Starting AssessmentSummaryController.calculate for userId = {} and year = {}",
                                userId, year);
                var assessmentSummary = assessmentSummaryService
                                .calculateAssessmentSummaryButValidateTheUserIdFirstBeforeCalculating(
                                                userId, year);
                log.info("Ending AssessmentSummaryController.calculate for userId = {} and year = {}",
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
