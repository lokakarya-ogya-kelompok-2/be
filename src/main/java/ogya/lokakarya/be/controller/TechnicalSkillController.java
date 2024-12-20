package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillFilter;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillReq;
import ogya.lokakarya.be.service.TechnicalSkillService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/technical-skills")
public class TechnicalSkillController {
        @Autowired
        private TechnicalSkillService technicalSkillService;

        @PostMapping
        public ResponseEntity<ResponseDto<TechnicalSkillDto>> create(
                        @RequestBody @Valid TechnicalSkillReq data) {
                log.info("Starting TechnicalSkillController.create");
                var createdTechnicalSkill = technicalSkillService.create(data);
                log.info("Ending TechnicalSkillController.create");
                return ResponseDto.<TechnicalSkillDto>builder().content(createdTechnicalSkill)
                                .message("Create Technical Skill Succesful").success(true).build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<TechnicalSkillDto>>> getAllTechnicalSkills(
                        @RequestParam(name = "name_contains", required = false) String nameContains,
                        @RequestParam(name = "enabled_only", required = false,
                                        defaultValue = "false") Boolean enabledOnly,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy,
                        @RequestParam(name = "page_number", required = false) Integer pageNumber,
                        @RequestParam(name = "page_size", required = false,
                                        defaultValue = "5") Integer pageSize) {
                log.info("Starting TechnicalSkillController.list");
                TechnicalSkillFilter filter = new TechnicalSkillFilter();
                filter.setNameContains(nameContains);
                filter.setEnabledOnly(enabledOnly);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                filter.setPageNumber(pageNumber);
                filter.setPageSize(pageSize);

                Page<TechnicalSkillDto> technicalSkills =
                                technicalSkillService.getAlltechnicalSkills(filter);
                log.info("Ending TechnicalSkillController.list");
                return ResponseDto.<List<TechnicalSkillDto>>builder()
                                .content(technicalSkills.toList())
                                .totalRecords(technicalSkills.getTotalElements())
                                .totalPages(technicalSkills.getTotalPages())
                                .pageNumber(technicalSkills.getNumber() + 1)
                                .pageSize(technicalSkills.getSize())
                                .message("Get All Technical Skill successful!").success(true)
                                .build().toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<TechnicalSkillDto>> getTechnicalSkillById(
                        @PathVariable UUID id) {
                log.info("Starting TechnicalSkillController.get for id = {}", id);
                TechnicalSkillDto response = technicalSkillService.gettechnicalSkillById(id);
                log.info("Starting TechnicalSkillController.get for id = {}", id);
                return ResponseDto.<TechnicalSkillDto>builder().content(response)
                                .message(String.format("Get Technical Skill with id %s successful!",
                                                id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<TechnicalSkillDto>> updatetechnicalSkillById(
                        @PathVariable UUID id,
                        @RequestBody @Valid TechnicalSkillReq technicalSkillReq) {
                log.info("Starting TechnicalSkillController.update for id = {}", id);
                TechnicalSkillDto res = technicalSkillService.updateTechnicalSkillById(id,
                                technicalSkillReq);
                log.info("Ending TechnicalSkillController.update for id = {}", id);
                return ResponseDto.<TechnicalSkillDto>builder().content(res)
                                .message(String.format("Update user with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteTechnicalSkillById(@PathVariable UUID id) {
                log.info("Starting TechnicalSkillController.delete for id = {}", id);
                technicalSkillService.deleteTechnicalSkillById(id);
                log.info("Ending TechnicalSkillController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true).message(
                                String.format("Delete technical skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
