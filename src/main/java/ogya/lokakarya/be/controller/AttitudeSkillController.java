package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
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
import ogya.lokakarya.be.dto.FilterInfo;
import ogya.lokakarya.be.dto.PageInfo;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillFilter;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillReq;
import ogya.lokakarya.be.service.AttitudeSkillService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/attitude-skills")
public class AttitudeSkillController {
        @Autowired
        AttitudeSkillService attitudeSkillService;

        @PostMapping
        public ResponseEntity<ResponseDto<AttitudeSkillDto>> create(
                        @RequestBody @Valid AttitudeSkillReq data) {
                log.info("Starting AttitudeSkillController.create");
                var createdAttitudeSkill = attitudeSkillService.create(data);
                log.info("Ending AttitudeSkillController.create");
                return ResponseDto.<AttitudeSkillDto>builder().success(true)
                                .content(createdAttitudeSkill)
                                .message("Create attitude skill successful!").build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<AttitudeSkillDto>>> getAllAttitudeSKills(
                        @RequestParam(name = "any_contains",
                                        required = false) String anyStringFieldContains,
                        @RequestParam(name = "name_contains", required = false) String nameContains,
                        @RequestParam(name = "group_ids", required = false) List<UUID> groupIds,
                        @RequestParam(name = "enabled_only", required = false,
                                        defaultValue = "false") Boolean enabledOnly,
                        @RequestParam(name = "with_group", required = false,
                                        defaultValue = "false") Boolean withGroup,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy,
                        @RequestParam(name = "page_number", required = false) Integer pageNumber,
                        @RequestParam(name = "page_size", required = false,
                                        defaultValue = "5") Integer pageSize,
                        @RequestParam(name = "sort_field", required = false,
                                        defaultValue = "createdAt") String sortField,
                        @RequestParam(name = "sort_direction", required = false,
                                        defaultValue = "DESC") Direction sortDirection) {
                log.info("Starting AttitudeSkillController.list");
                AttitudeSkillFilter filter = new AttitudeSkillFilter();
                filter.setAnyStringFieldContains(anyStringFieldContains);
                filter.setNameContains(nameContains);
                filter.setGroupIds(groupIds);
                filter.setEnabledOnly(enabledOnly);
                filter.setWithGroup(withGroup);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                filter.setPageNumber(pageNumber);
                filter.setPageSize(pageSize);
                filter.setSortField(sortField);
                filter.setSortDirection(sortDirection);

                Page<AttitudeSkillDto> attitudeSkills =
                                attitudeSkillService.getAllAttitudeSkills(filter);
                log.info("Ending AttitudeSkillController.list");
                return ResponseDto.<List<AttitudeSkillDto>>builder().success(true)
                                .content(attitudeSkills.toList())
                                .pageInfo(new PageInfo(attitudeSkills.getNumber() + 1,
                                                attitudeSkills.getSize(),
                                                attitudeSkills.getTotalPages(),
                                                attitudeSkills.getTotalElements()))
                                .filterInfo(new FilterInfo("id", "name", "groupAttitudeSkill",
                                                "createdAt", "createdBy", "updatedAt", "updatedBy"))
                                .message("List all attitude skill successful!").build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<AttitudeSkillDto>> getAttitudeSkillById(
                        @PathVariable UUID id) {
                log.info("Starting AttitudeSkillController.get for id = {}", id);
                AttitudeSkillDto attitudeSkill = attitudeSkillService.getAttitudeSkillById(id);
                log.info("Ending AttitudeSkillController.get for id = {}", id);
                return ResponseDto.<AttitudeSkillDto>builder().success(true).content(attitudeSkill)
                                .message(String.format("Get attitude skill with id %s successful!",
                                                id))
                                .build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<AttitudeSkillDto>> updateDivisionById(
                        @PathVariable UUID id,
                        @RequestBody @Valid AttitudeSkillReq attitudeSkillReq) {
                log.info("Starting AttitudeSkillController.update for id = {}", id);
                AttitudeSkillDto updatedAttitudeSkill =
                                attitudeSkillService.updateAttitudeSkillById(id, attitudeSkillReq);
                log.info("Ending AttitudeSkillController.update for id = {}", id);
                return ResponseDto.<AttitudeSkillDto>builder().success(true)
                                .content(updatedAttitudeSkill)
                                .message(String.format(
                                                "Update attitude skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteAttitudeSkillById(@PathVariable UUID id) {
                log.info("Starting AttitudeSkillController.delete for id = {}", id);
                attitudeSkillService.deleteAttitudeSkillById(id);
                log.info("Ending AttitudeSkillController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true)
                                .message(String.format(
                                                "Delete attitude skill with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
