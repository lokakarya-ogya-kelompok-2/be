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
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanDto;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanFilter;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanReq;
import ogya.lokakarya.be.service.EmpDevPlanService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/emp-dev-plans")
public class EmpDevPlanController {
    @Autowired
    private EmpDevPlanService empDevPlanService;

    @PostMapping
    public ResponseEntity<ResponseDto<EmpDevPlanDto>> create(
                    @RequestBody @Valid EmpDevPlanReq data) {
            log.info("Starting EmpDevPlanController.create");
            var createdEmpDevPlan = empDevPlanService.create(data);
            log.info("Ending EmpDevPlanController.create");
        return ResponseDto.<EmpDevPlanDto>builder().content(createdEmpDevPlan)
                .message("Create emp dev plan successful!").success(true).build()
                .toResponse(HttpStatus.CREATED);
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<ResponseDto<List<EmpDevPlanDto>>> createBulk(
                    @RequestBody @Valid List<EmpDevPlanReq> data) {
            log.info("Starting EmpDevPlanController.createBulk");
            var createdEmpDevPlans = empDevPlanService.createBulkEmpDevPlan(data);
            log.info("Ending EmpDevPlanController.createBulk");
        return ResponseDto.<List<EmpDevPlanDto>>builder().success(true)
                .message("Create all emp development plans successful!").content(createdEmpDevPlans)
                .build().toResponse(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<EmpDevPlanDto>>> getAllEmpDevPlans(
            @RequestParam(name = "user_ids", required = false) List<UUID> userIds,
            @RequestParam(required = false) List<Integer> years,
            @RequestParam(name = "enabled_only", required = false,
                    defaultValue = "false") Boolean enabledOnly,
            @RequestParam(name = "with_created_by", required = false,
                    defaultValue = "false") Boolean withCreatedBy,
            @RequestParam(name = "with_updated_by", required = false,
                    defaultValue = "false") Boolean withUpdatedBy) {
            log.info("Starting EmpDevPlanController.list");
        EmpDevPlanFilter filter = new EmpDevPlanFilter();
        filter.setUserIds(userIds);
        filter.setYears(years);
        filter.setEnabledOnly(enabledOnly);
        filter.setWithCreatedBy(withCreatedBy);
        filter.setWithUpdatedBy(withUpdatedBy);

        List<EmpDevPlanDto> data = empDevPlanService.getAllEmpDevPlans(filter);
        log.info("Ending EmpDevPlanController.list");
        return ResponseDto.<List<EmpDevPlanDto>>builder().content(data)
                .message("Get emp dev plans successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpDevPlanDto>> getEmpDevPlanById(@PathVariable UUID id) {
            log.info("Starting EmpDevPlanController.get for id = {}", id);
            EmpDevPlanDto response = empDevPlanService.getEmpDevPlanById(id);
            log.info("Ending EmpDevPlanController.get for id = {}", id);
        return ResponseDto.<EmpDevPlanDto>builder().content(response)
                .message(String.format("Get emp dev plan with id %s successful!", id)).success(true)
                .build().toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpDevPlanDto>> updateEmpDevPlanById(@PathVariable UUID id,
                    @RequestBody @Valid EmpDevPlanReq empDevPlanReq) {
            log.info("Starting EmpDevPlanController.update for id = {}", id);
            EmpDevPlanDto res = empDevPlanService.updateEmpDevPlanById(id, empDevPlanReq);
            log.info("Ending EmpDevPlanController.update for id = {}", id);
        return ResponseDto.<EmpDevPlanDto>builder().content(res)
                .message(String.format("Update emp dev plan with id %s successful!", id))
                .success(true).build().toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteEmpDevPlanById(@PathVariable UUID id) {
        log.info("Starting EmpDevPlanController.delete for id = {}", id);
        empDevPlanService.deleteEmpDevPlanById(id);
        log.info("Ending EmpDevPlanController.update for id = {}", id);
        return ResponseDto.<Void>builder().success(true)
                        .message(String.format("Delete emp dev plan with id %s successful!", id))
                        .build().toResponse(HttpStatus.OK);
    }
}
