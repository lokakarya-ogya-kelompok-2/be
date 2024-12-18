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
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillDto;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillFilter;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillReq;
import ogya.lokakarya.be.service.EmpAttitudeSkillService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/emp-attitude-skills")
public class EmpAttitudeSkillController {
    @Autowired
    EmpAttitudeSkillService empAttitudeSkillService;

    @PostMapping
    public ResponseEntity<ResponseDto<EmpAttitudeSkillDto>> create(
                    @RequestBody @Valid EmpAttitudeSkillReq data) {
            log.info("Starting EmpAttitudeSkillController.create");
            var createdEmpAttitudeSkill = empAttitudeSkillService.create(data);
            log.info("Ending EmpAttitudeSkillController.create");
        return ResponseDto.<EmpAttitudeSkillDto>builder().content(createdEmpAttitudeSkill)
                .message("Create emp attitude skill successful!").success(true).build()
                .toResponse(HttpStatus.CREATED);
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<ResponseDto<List<EmpAttitudeSkillDto>>> createBulk(
            @RequestBody @Valid List<EmpAttitudeSkillReq> data) {
            log.info("Starting EmpAttitudeSkillController.createBulk");
            var createdEmpAttitudeSkill = empAttitudeSkillService.createBulkEmpAttitudeSkill(data);
            log.info("Ending EmpAttitudeSkillController.createBulk");
        return ResponseDto.<List<EmpAttitudeSkillDto>>builder().success(true)
                .message("Create all emp technical skills successful!")
                .content(createdEmpAttitudeSkill).build().toResponse(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<EmpAttitudeSkillDto>>> getAllEmpAttitudeSkills(
            @RequestParam(name = "user_ids", required = false) List<UUID> userIds,
            @RequestParam(name = "years", required = false) List<Integer> years,
            @RequestParam(name = "enabled_only", required = false,
                    defaultValue = "false") Boolean enabledOnly,
            @RequestParam(name = "with_created_by", required = false,
                    defaultValue = "false") Boolean withCreatedBy,
            @RequestParam(name = "with_updated_by", required = false,
                    defaultValue = "false") Boolean withUpdatedBy) {
                        log.info("Starting EmpAttitudeSkillController.list");
        EmpAttitudeSkillFilter filter = new EmpAttitudeSkillFilter();
        filter.setUserIds(userIds);
        filter.setYears(years);
        filter.setEnabledOnly(enabledOnly);
        filter.setWithCreatedBy(withCreatedBy);
        filter.setWithUpdatedBy(withUpdatedBy);

        List<EmpAttitudeSkillDto> data =
                        empAttitudeSkillService.getAllEmpAttitudeSkills(filter);
                        log.info("Ending EmpAttitudeSkillController.list");
                return ResponseDto.<List<EmpAttitudeSkillDto>>builder().content(data)
                .message("Get emp attitude skills successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpAttitudeSkillDto>> getEmpAttitudeSkillById(
                    @PathVariable UUID id) {
            log.info("Starting EmpAttitudeSkillController.get for id = {}", id);
            EmpAttitudeSkillDto response = empAttitudeSkillService.getEmpAttitudeSkillById(id);
            log.info("Ending EmpAttitudeSkillController.get for id = {}", id);
        return ResponseDto.<EmpAttitudeSkillDto>builder().content(response)
                .message(String.format("Get emp attitude skill with id %s successful!", id))
                .success(true).build().toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpAttitudeSkillDto>> updateEmpAttitudeSkillById(
                    @PathVariable UUID id,
                    @RequestBody @Valid EmpAttitudeSkillReq empAttitudeSkillReq) {
            log.info("Starting EmpAttitudeSkillController.update for id = {}", id);
        EmpAttitudeSkillDto res =
                        empAttitudeSkillService.updateEmpAttitudeSkillById(id, empAttitudeSkillReq);
        log.info("Ending EmpAttitudeSkillController.update for id = {}", id);
        return ResponseDto.<EmpAttitudeSkillDto>builder().content(res)
                .message(String.format("Update emp attitude skill with id %s successful!", id))
                .success(true).build().toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteEmpAttitudeSkillById(@PathVariable UUID id) {
            log.info("Starting EmpattitudeSkillController.delete for id = {}", id);
            empAttitudeSkillService.deleteEmpAttitudeSkillById(id);
            log.info("Ending EmpAttitudeSkillController.delete for id = {}", id);
            return ResponseDto.<Void>builder().success(true)
                            .message(String.format(
                                            "Delete emp attitude skill with id %s successful!", id))
                            .build().toResponse(HttpStatus.OK);
    }
}
