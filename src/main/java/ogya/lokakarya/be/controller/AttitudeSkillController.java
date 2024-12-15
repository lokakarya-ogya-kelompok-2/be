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
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillFilter;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillReq;
import ogya.lokakarya.be.service.AttitudeSkillService;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/attitude-skills")
public class AttitudeSkillController {
        @Autowired
        AttitudeSkillService attitudeSkillService;

        @PostMapping
        public ResponseEntity<ResponseDto<AttitudeSkillDto>> create(
                        @RequestBody @Valid AttitudeSkillReq data) {
                var createdAttitudeSkill = attitudeSkillService.create(data);
                return ResponseDto.<AttitudeSkillDto>builder().success(true)
                                .content(createdAttitudeSkill)
                                .message("Create attitude skill successful!").build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<AttitudeSkillDto>>> getAllAttitudeSKills(
                        @RequestParam(name = "name_contains", required = false) String nameContains,
                        @RequestParam(name = "enabled_only", required = false,
                                        defaultValue = "false") Boolean enabledOnly,
                        @RequestParam(name = "with_group", required = false,
                                        defaultValue = "false") Boolean withGroup,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy) {
                AttitudeSkillFilter filter = new AttitudeSkillFilter();
                filter.setNameContains(nameContains);
                filter.setEnabledOnly(enabledOnly);
                filter.setWithGroup(withGroup);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                List<AttitudeSkillDto> attitudeSkills =
                                attitudeSkillService.getAllAttitudeSkills(filter);
                return ResponseDto.<List<AttitudeSkillDto>>builder().success(true)
                                .content(attitudeSkills)
                                .message("List all attitude skill successful!").build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<AttitudeSkillDto>> getAttitudeSkillById(
                        @PathVariable UUID id) {
                AttitudeSkillDto attitudeSkill = attitudeSkillService.getAttitudeSkillById(id);
                return ResponseDto.<AttitudeSkillDto>builder().success(true).content(attitudeSkill)
                                .message(String.format("Get attitude skill with id %s successful!",
                                                id))
                                .build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<AttitudeSkillDto>> updateDivisionById(
                        @PathVariable UUID id,
                        @RequestBody @Valid AttitudeSkillReq attitudeSkillReq) {
                AttitudeSkillDto updatedAttitudeSkill =
                                attitudeSkillService.updateAttitudeSkillById(id, attitudeSkillReq);
                return ResponseDto.<AttitudeSkillDto>builder().success(true)
                                .content(updatedAttitudeSkill)
                                .message(String.format(
                                                "Update attitude skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteAttitudeSkillById(@PathVariable UUID id) {
                attitudeSkillService.deleteAttitudeSkillById(id);
                return ResponseDto.<Void>builder().success(true)
                                .message(String.format(
                                                "Delete attitude skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
