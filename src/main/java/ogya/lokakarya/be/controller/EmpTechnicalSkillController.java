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
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillFilter;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;
import ogya.lokakarya.be.service.EmpTechnicalSkillService;

@RequestMapping("/emp-technical-skills")
@RestController
public class EmpTechnicalSkillController {
        @Autowired
        private EmpTechnicalSkillService empTechnicalSkillService;

        @PostMapping
        public ResponseEntity<ResponseDto<EmpTechnicalSkillDto>> create(
                        @RequestBody @Valid EmpTechnicalSkillReq data) {
                var createdEmpTechnicalSkill = empTechnicalSkillService.create(data);
                return ResponseDto.<EmpTechnicalSkillDto>builder().content(createdEmpTechnicalSkill)
                                .message("Create emp technical skill successful!").success(true)
                                .build().toResponse(HttpStatus.CREATED);
        }

        @PostMapping("/bulk-create")
        public ResponseEntity<ResponseDto<List<EmpTechnicalSkillDto>>> createBulk(
                        @RequestBody @Valid List<EmpTechnicalSkillReq> data) {
                System.out.println("DATA: " + data);
                var createdEmpTechSkills = empTechnicalSkillService.createBulk(data);
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
                System.out.println("Get All Emp Technical Skills");

                EmpTechnicalSkillFilter filter = new EmpTechnicalSkillFilter();
                filter.setUserIds(userIds);
                filter.setYears(years);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);

                List<EmpTechnicalSkillDto> response =
                                empTechnicalSkillService.getAllEmpTechnicalSkills(filter);
                return ResponseDto.<List<EmpTechnicalSkillDto>>builder().content(response)
                                .message("Get all emp technical skills successful!").success(true)
                                .build().toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<EmpTechnicalSkillDto>> getEmpTechnicalSkillById(
                        @PathVariable UUID id) {
                EmpTechnicalSkillDto response =
                                empTechnicalSkillService.getEmpTechnicalSkillById(id);
                return ResponseDto.<EmpTechnicalSkillDto>builder().content(response).message(
                                String.format("Get emp technical skill with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<EmpTechnicalSkillDto>> updateEmpTechnicalSkillById(
                        @PathVariable UUID id,
                        @RequestBody @Valid EmpTechnicalSkillReq empTechnicalSkillReq) {
                EmpTechnicalSkillDto res = empTechnicalSkillService.updateEmpTechnicalSkillById(id,
                                empTechnicalSkillReq);
                return ResponseDto.<EmpTechnicalSkillDto>builder().content(res).message(String
                                .format("Update emp technical skill with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteEmpTechnicalSkillById(
                        @PathVariable UUID id) {
                empTechnicalSkillService.deleteEmpTechnicalSkillById(id);
                return ResponseDto.<Void>builder().success(true).message(
                                String.format("Delete technical skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
