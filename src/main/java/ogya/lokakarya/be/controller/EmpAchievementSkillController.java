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
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillFilter;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillReq;
import ogya.lokakarya.be.service.EmpAchievementSkillService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/emp-achievement-skills")
public class EmpAchievementSkillController {
        @Autowired
        EmpAchievementSkillService achievementSkillService;

        @PostMapping
        public ResponseEntity<ResponseDto<EmpAchievementSkillDto>> create(
                        @RequestBody @Valid EmpAchievementSkillReq data) {
                log.info("Starting EmpAchievementSkillController.create");
                var createdAchievementSkill = achievementSkillService.create(data);
                log.info("Ending EmpAchievementSkillController.create");
                return ResponseDto.<EmpAchievementSkillDto>builder()
                                .content(createdAchievementSkill)
                                .message("Create emp achievement skill successful!").success(true)
                                .build().toResponse(HttpStatus.CREATED);
        }

        @PostMapping("/bulk-create")
        public ResponseEntity<ResponseDto<List<EmpAchievementSkillDto>>> createBulk(
                        @RequestBody @Valid List<EmpAchievementSkillReq> data) {
                log.info("Starting EmpAchievementSkillController.createBulk");
                var createdAchievementSkill = achievementSkillService.createBulk(data);
                log.info("Ending EmpAchievementSkillController.createBulk");
                return ResponseDto.<List<EmpAchievementSkillDto>>builder()
                                .content(createdAchievementSkill)
                                .message("Create emp achievement skill successful!").success(true)
                                .build().toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<EmpAchievementSkillDto>>> getAllAchievementSkills(
                        @RequestParam(name = "user_ids", required = false) List<UUID> userIds,
                        @RequestParam(required = false) List<Integer> years,
                        @RequestParam(name = "enabled_only", required = false,
                                        defaultValue = "false") Boolean enabledOnly,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy) {
                log.info("Starting EmpAchievementSkillController.list");
                EmpAchievementSkillFilter filter = new EmpAchievementSkillFilter();
                filter.setUserIds(userIds);
                filter.setYears(years);
                filter.setEnabledOnly(enabledOnly);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);

                List<EmpAchievementSkillDto> response =
                                achievementSkillService.getAllAchievementSkills(filter);
                log.info("Ending EmpAchievementSkillController.list");
                return ResponseDto.<List<EmpAchievementSkillDto>>builder().content(response)
                                .message("Get emp achievement skills successful!").success(true)
                                .build().toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<EmpAchievementSkillDto>> getAchievementSkillById(
                        @PathVariable UUID id) {
                log.info("Starting EmpAchievementSkillController.get for id = {}", id);
                EmpAchievementSkillDto response =
                                achievementSkillService.getAchievementSkillById(id);
                log.info("Ending EmpAchievementSkillController.get for id = {}", id);
                return ResponseDto.<EmpAchievementSkillDto>builder().content(response)
                                .message(String.format(
                                                "Get emp achievement skill with id %s successful!",
                                                id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<EmpAchievementSkillDto>> updateAchievementSkillById(
                        @PathVariable UUID id,
                        @RequestBody @Valid EmpAchievementSkillReq empAchievementSkillReq) {
                log.info("Starting EmpAchievementSkillController.update for id = {}", id);
                EmpAchievementSkillDto res = achievementSkillService.updateAchievementSkillById(id,
                                empAchievementSkillReq);
                log.info("Ending EmpAchievementSkillController.update for id = {}", id);
                return ResponseDto.<EmpAchievementSkillDto>builder().content(res).message(String
                                .format("Update emp achievement skill with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteAchievementSkillById(@PathVariable UUID id) {
                log.info("Starting EmpAchievementSkillController.delete for id = {}", id);
                achievementSkillService.deleteAchievementSkillById(id);
                log.info("Ending EmpAchievementSkillController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true).message(String
                                .format("Delete emp achievement skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
