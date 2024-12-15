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
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillFilter;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillReq;
import ogya.lokakarya.be.service.GroupAttitudeSkillService;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/group-attitude-skills")
public class GroupAttitudeSkillController {
        @Autowired
        private GroupAttitudeSkillService groupAttitudeSkillService;

        @PostMapping
        public ResponseEntity<ResponseDto<GroupAttitudeSkillDto>> create(
                        @RequestBody @Valid GroupAttitudeSkillReq data) {
                var createdGroupAttitudeSill = groupAttitudeSkillService.create(data);
                return ResponseDto.<GroupAttitudeSkillDto>builder()
                                .content(createdGroupAttitudeSill)
                                .message("Create group attitude skill successful!").success(true)
                                .build().toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<GroupAttitudeSkillDto>>> getAllGroupAttitudeSkills(
                        @RequestParam(name = "group_name_contains",
                                        required = false) String groupNameContains,
                        @RequestParam(name = "min_weight", required = false) Integer minWeight,
                        @RequestParam(name = "max_weight", required = false) Integer maxWeight,
                        @RequestParam(name = "enabled_only", required = false,
                                        defaultValue = "false") Boolean enabledOnly,
                        @RequestParam(name = "with_attitude_skills", required = false,
                                        defaultValue = "false") Boolean withAttitudeSkills,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy) {

                System.out.println("Get All Group Attitude Skills");
                GroupAttitudeSkillFilter filter = new GroupAttitudeSkillFilter();
                filter.setGroupNameContains(groupNameContains);
                filter.setMinWeight(minWeight);
                filter.setMaxWeight(maxWeight);
                filter.setEnabledOnly(enabledOnly);
                filter.setWithAttitudeSkills(withAttitudeSkills);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);

                List<GroupAttitudeSkillDto> response =
                                groupAttitudeSkillService.getAllGroupAttitudeSkills(filter);
                return ResponseDto.<List<GroupAttitudeSkillDto>>builder().content(response)
                                .message("Get all group attitude skill successful!").success(true)
                                .build().toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<GroupAttitudeSkillDto>> getGroupAttitudeSkillById(
                        @PathVariable UUID id) {
                GroupAttitudeSkillDto response =
                                groupAttitudeSkillService.getGroupAttitudeSkillById(id);
                return ResponseDto.<GroupAttitudeSkillDto>builder().content(response).message(String
                                .format("Get group attitude skill with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<GroupAttitudeSkillDto>> updateDivisionById(
                        @PathVariable UUID id,
                        @RequestBody @Valid GroupAttitudeSkillReq groupAttitudeSkillReq) {
                GroupAttitudeSkillDto res = groupAttitudeSkillService
                                .updateGroupAttitudeSkillById(id, groupAttitudeSkillReq);
                return ResponseDto.<GroupAttitudeSkillDto>builder().content(res).message(String
                                .format("Update group attitude skill with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteGroupAttitudeById(@PathVariable UUID id) {
                groupAttitudeSkillService.deleteGroupAttitudeSkillById(id);
                return ResponseDto.<Void>builder().success(true).message(String
                                .format("Delete group attitude skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
