package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillFilter;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillReq;
import ogya.lokakarya.be.service.EmpAchievementSkillService;
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

import java.util.List;
import java.util.UUID;

@RequestMapping("/emp-achievement-skills")
@RestController
public class    EmpAchievementSkillController {
        @Autowired
        EmpAchievementSkillService achievementSkillService;

        @PostMapping
        public ResponseEntity<ResponseDto<EmpAchievementSkillDto>> create(
                        @RequestBody @Valid EmpAchievementSkillReq data) {
                var createdAchievementSkill = achievementSkillService.create(data);
                return ResponseDto.<EmpAchievementSkillDto>builder()
                                .content(createdAchievementSkill)
                                .message("Create emp achievement skill successful!").success(true)
                                .build().toResponse(HttpStatus.CREATED);
        }

        @PostMapping("/bulk-create")
        public ResponseEntity<ResponseDto<List<EmpAchievementSkillDto>>> create(
                        @RequestBody @Valid List<EmpAchievementSkillReq> data) {
                var createdAchievementSkill = achievementSkillService.createBulk(data);
                return ResponseDto.<List<EmpAchievementSkillDto>>builder()
                                .content(createdAchievementSkill)
                                .message("Create emp achievement skill successful!").success(true)
                                .build().toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<EmpAchievementSkillDto>>> getAllAchievementSkills(
                        @RequestParam(name = "user_ids", required = false) List<UUID> userIds,
                        @RequestParam(required = false) List<Integer> years,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy) {
                System.out.println("Get All Achievement Skill");
                EmpAchievementSkillFilter filter = new EmpAchievementSkillFilter();
                filter.setUserIds(userIds);
                filter.setYears(years);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                List<EmpAchievementSkillDto> response =
                                achievementSkillService.getAllAchievementSkills(filter);
                return ResponseDto.<List<EmpAchievementSkillDto>>builder().content(response)
                                .message("Get emp achievement skills successful!").success(true)
                                .build().toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<EmpAchievementSkillDto>> getAchievementSkillById(
                        @PathVariable UUID id) {
                EmpAchievementSkillDto response =
                                achievementSkillService.getAchievementSkillById(id);
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
                EmpAchievementSkillDto res = achievementSkillService.updateAchievementSkillById(id,
                                empAchievementSkillReq);
                return ResponseDto.<EmpAchievementSkillDto>builder().content(res).message(String
                                .format("Update emp achievement skill with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteAchievementSkillById(@PathVariable UUID id) {
                achievementSkillService.deleteAchievementSkillById(id);
                return ResponseDto.<Void>builder().success(true).message(String
                                .format("Delete achievement skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
