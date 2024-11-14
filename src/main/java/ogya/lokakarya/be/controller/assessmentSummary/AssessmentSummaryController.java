package ogya.lokakarya.be.controller.assessmentSummary;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.assessmentSummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentSummary.CreateAssessmentSummary;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/assessment-summary")
@RestController
public class AssessmentSummaryController {
    @Autowired
    private AssessmentSummaryService assessmentSummaryService;

    @PostMapping
    public ResponseEntity<AssessmentSummary> createAssessmentSummary
            (@RequestBody @Valid CreateAssessmentSummary data) {
        var createAssessmentSummary= assessmentSummaryService.create(data);
        return new ResponseEntity<>(createAssessmentSummary, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<AssessmentSummaryDto>> getAllAssessmentSummaries() {
        System.out.println("Get All Assessment Summary");
        List<AssessmentSummaryDto> response = assessmentSummaryService.getAllAssessmentSummaries();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AssessmentSummaryDto> getAssessmentSummaryById(@PathVariable UUID id) {
        AssessmentSummaryDto response = assessmentSummaryService.getAssessmentSummaryById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AssessmentSummaryDto> updateAssessmentSummaryById
            (@PathVariable UUID id, @RequestBody @Valid CreateAssessmentSummary createAssessmentSummary) {
        AssessmentSummaryDto res= assessmentSummaryService.updateAssessmentSummaryById(id, createAssessmentSummary);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAssessmentSummaryById(@PathVariable UUID id) {
        boolean res= assessmentSummaryService.deleteAssessmentSummaryById(id) ;
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
