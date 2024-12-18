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
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionFilter;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionReq;
import ogya.lokakarya.be.service.EmpSuggestionService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/emp-suggestions")
public class EmpSuggestionController {
    @Autowired
    private EmpSuggestionService empSuggestionService;

    @PostMapping
    public ResponseEntity<ResponseDto<EmpSuggestionDto>> create(
                    @RequestBody @Valid EmpSuggestionReq data) {
            log.info("Starting EmpSuggestionController.create");
            var createdEmpSuggestion = empSuggestionService.create(data);
            log.info("Ending EmpSuggestionController.create");
        return ResponseDto.<EmpSuggestionDto>builder().content(createdEmpSuggestion)
                .message("Create emp suggestion successful!").success(true).build()
                .toResponse(HttpStatus.CREATED);
    }
    @PostMapping("/bulk-create")
    public ResponseEntity<ResponseDto<List<EmpSuggestionDto>>> createBulk(
            @RequestBody @Valid List<EmpSuggestionReq> data) {
            log.info("Starting EmpSuggestionController.createBulk");
            var createdEmpSuggestion = empSuggestionService.createBulk(data);
            log.info("Ending EmpSuggestionController.createBulk");
        return ResponseDto.<List<EmpSuggestionDto>>builder().success(true)
                .message("Create all emp suggestions successful!")
                .content(createdEmpSuggestion).build().toResponse(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ResponseDto<List<EmpSuggestionDto>>> getAllEmpSuggestions(
            @RequestParam(name = "user_ids", required = false) List<UUID> userIds,
            @RequestParam(required = false) List<Integer> years,
            @RequestParam(name = "with_created_by", required = false,
                    defaultValue = "false") Boolean withCreatedBy,
            @RequestParam(name = "with_updated_by", required = false,
                    defaultValue = "false") Boolean withUpdatedBy
    ) {
        log.info("Starting EmpSuggestionController.list");
        EmpSuggestionFilter filter = new EmpSuggestionFilter();
        filter.setUserIds(userIds);
        filter.setYears(years);
        filter.setWithCreatedBy(withCreatedBy);
        filter.setWithUpdatedBy(withUpdatedBy);

        List<EmpSuggestionDto> response = empSuggestionService.getAllEmpSuggestions(filter);
        log.info("Ending EmpSuggestionController.list");
        return ResponseDto.<List<EmpSuggestionDto>>builder().content(response)
                .message("Get all emp suggestions successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpSuggestionDto>> getEmpSuggestionById(
                    @PathVariable UUID id) {
            log.info("Starting EmpSuggestionController.get for id = {}", id);
            EmpSuggestionDto response = empSuggestionService.getEmpSuggestionById(id);
            log.info("Ending EmpSuggestionController.get for id = {}", id);
        return ResponseDto.<EmpSuggestionDto>builder().content(response)
                .message(String.format("Get emp suggestion with id %s successful!", id)).success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpSuggestionDto>> updateEmpSuggestionById
    (@PathVariable UUID id, @RequestBody @Valid EmpSuggestionReq empSuggestionReq) {
            log.info("Starting EmpSuggestionController.update for id = {}", id);
            EmpSuggestionDto res =
                            empSuggestionService.updateEmpSuggestionById(id, empSuggestionReq);
                            log.info("Ending EmpSuggestionController.update for id = {}", id);
        return ResponseDto.<EmpSuggestionDto>builder().content(res)
                .message(String.format("Update emp suggestion with id %s successful!", id)).success(true)
                .build().toResponse(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteEmpSuggestionById(@PathVariable UUID id) {
        log.info("Starting EmpSuggestionController.delete for id = {}", id);
        empSuggestionService.deleteEmpSuggestionById(id);
        log.info("Ending EmpSuggestionController.delete for id = {}", id);
        return ResponseDto.<Void>builder().success(true)
                        .message(String.format("Delete emp suggestion with id %s successful!", id))
                        .build().toResponse(HttpStatus.OK);
    }
}
