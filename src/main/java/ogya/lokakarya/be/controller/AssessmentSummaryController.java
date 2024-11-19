package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryReq;
import ogya.lokakarya.be.service.AssessmentSummaryService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/assessment-summaries")
@RestController
public class AssessmentSummaryController {
    @Autowired
    private AssessmentSummaryService assessmentSummaryService;

    @PostMapping
    public ResponseEntity<AssessmentSummaryDto> createAssessmentSummary
            (@RequestBody @Valid AssessmentSummaryReq data) {
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
            (@PathVariable UUID id, @RequestBody @Valid AssessmentSummaryReq assessmentSummaryReq) {
        AssessmentSummaryDto res= assessmentSummaryService.updateAssessmentSummaryById(id, assessmentSummaryReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAssessmentSummaryById(@PathVariable UUID id) {
        boolean res= assessmentSummaryService.deleteAssessmentSummaryById(id) ;
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
