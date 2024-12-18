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
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillFilter;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;
import ogya.lokakarya.be.service.EmpTechnicalSkillService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/emp-technical-skills")
public class EmpTechnicalSkillController {
        @Autowired
        private EmpTechnicalSkillService empTechnicalSkillService;

        @PostMapping
        public ResponseEntity<ResponseDto<EmpTechnicalSkillDto>> create(
                        @RequestBody @Valid EmpTechnicalSkillReq data) {
                log.info("Starting EmpTechnicalSkillController.create");
                var createdEmpTechnicalSkill = empTechnicalSkillService.create(data);
                log.info("Ending EmpTechnicalSkillController.create");
                return ResponseDto.<EmpTechnicalSkillDto>builder().content(createdEmpTechnicalSkill)
                                .message("Create emp technical skill successful!").success(true)
                                .build().toResponse(HttpStatus.CREATED);
        }

        @PostMapping("/bulk-create")
        public ResponseEntity<ResponseDto<List<EmpTechnicalSkillDto>>> createBulk(
                        @RequestBody @Valid List<EmpTechnicalSkillReq> data) {
                log.info("Starting EmpTechnicalSkillController.createBulk");
                var createdEmpTechSkills = empTechnicalSkillService.createBulk(data);
                log.info("Ending EmpTechnicalSkillController.createBulk");
                return ResponseDto.<List<EmpTechnicalSkillDto>>builder().success(true)
                                .message("Create all emp technical skills successful!")
                                .content(createdEmpTechSkills).build()
                                .toResponse(HttpStatus.CREATED);
        }


        @GetMapping
        public ResponseEntity<ResponseDto<List<EmpTechnicalSkillDto>>> getAllEmpTechnicalSkills(
                        @RequestParam(name = "user_ids", required = false) List<UUID> userIds,
                        @RequestParam(required = false) List<Integer> years,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy) {
                log.info("Starting EmpTechnicalSkillController.list");
                EmpTechnicalSkillFilter filter = new EmpTechnicalSkillFilter();
                filter.setUserIds(userIds);
                filter.setYears(years);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);

                List<EmpTechnicalSkillDto> response =
                                empTechnicalSkillService.getAllEmpTechnicalSkills(filter);
                log.info("Ending EmpTechnicalSkillController.list");
                return ResponseDto.<List<EmpTechnicalSkillDto>>builder().content(response)
                                .message("Get all emp technical skills successful!").success(true)
                                .build().toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<EmpTechnicalSkillDto>> getEmpTechnicalSkillById(
                        @PathVariable UUID id) {
                log.info("Starting EmpTechnicalSkillController.get for id = {}", id);
                EmpTechnicalSkillDto response =
                                empTechnicalSkillService.getEmpTechnicalSkillById(id);
                log.info("Ending EmpTechnicalSkillController.get for id = {}", id);
                return ResponseDto.<EmpTechnicalSkillDto>builder().content(response).message(
                                String.format("Get emp technical skill with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<EmpTechnicalSkillDto>> updateEmpTechnicalSkillById(
                        @PathVariable UUID id,
                        @RequestBody @Valid EmpTechnicalSkillReq empTechnicalSkillReq) {
                log.info("Starting EmpTechnicalSkillController.update for id = {}", id);
                EmpTechnicalSkillDto res = empTechnicalSkillService.updateEmpTechnicalSkillById(id,
                                empTechnicalSkillReq);
                log.info("Ending EmpTechnicalSkillController.update for id = {}", id);
                return ResponseDto.<EmpTechnicalSkillDto>builder().content(res).message(String
                                .format("Update emp technical skill with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteEmpTechnicalSkillById(
                        @PathVariable UUID id) {
                log.info("Starting EmpTechnicalSkillController.delete for id = {}", id);
                empTechnicalSkillService.deleteEmpTechnicalSkillById(id);
                log.info("Ending EmpTechnicalSkillController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true).message(String
                                .format("Delete emp technical skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
