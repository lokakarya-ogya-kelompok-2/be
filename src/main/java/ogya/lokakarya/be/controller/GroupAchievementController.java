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
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementDto;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementFilter;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementReq;
import ogya.lokakarya.be.service.GroupAchievementService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/group-achievements")
public class GroupAchievementController {
        @Autowired
        GroupAchievementService groupAchievementService;

        @PostMapping
        public ResponseEntity<ResponseDto<GroupAchievementDto>> create(
                        @RequestBody @Valid GroupAchievementReq data) {
                log.info("Starting GroupAchievementController.create");
                var createdGroupAchievement = groupAchievementService.create(data);
                log.info("Ending GroupAchievementController.create");
                return ResponseDto.<GroupAchievementDto>builder().content(createdGroupAchievement)
                                .message("Create group achievement successful!").success(true)
                                .build().toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<GroupAchievementDto>>> getAllGroupAchievements(
                        @RequestParam(name = "name_contains", required = false) String nameContains,
                        @RequestParam(name = "min_weight", required = false) Integer minWeight,
                        @RequestParam(name = "max_weight", required = false) Integer maxWeight,
                        @RequestParam(name = "enabled_only", required = false,
                                        defaultValue = "false") Boolean enabledOnly,
                        @RequestParam(name = "with_achievements", required = false,
                                        defaultValue = "false") Boolean withAchievements,
                        @RequestParam(name = "with_enabled_child_only", required = false,
                                        defaultValue = "false") Boolean withEnabledChildOnly,
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
                log.info("Starting GroupAchievementController.list");
                GroupAchievementFilter filter = new GroupAchievementFilter();
                filter.setNameContains(nameContains);
                filter.setMinWeight(minWeight);
                filter.setMaxWeight(maxWeight);
                filter.setEnabledOnly(enabledOnly);
                filter.setWithAchievements(withAchievements);
                filter.setWithEnabledChildOnly(withEnabledChildOnly);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                filter.setPageNumber(pageNumber);
                filter.setPageSize(pageSize);
                filter.setSortField(sortField);
                filter.setSortDirection(sortDirection);

                Page<GroupAchievementDto> groupAchievements =
                                groupAchievementService.getAllGroupAchievements(filter);
                log.info("Ending GroupAchievementController.list");
                return ResponseDto.<List<GroupAchievementDto>>builder()
                                .content(groupAchievements.toList())
                                .pageInfo(new PageInfo(groupAchievements.getNumber() + 1,
                                                groupAchievements.getSize(),
                                                groupAchievements.getTotalPages(),
                                                groupAchievements.getTotalElements()))
                                .filterInfo(new FilterInfo("id", "name", "weight", "createdAt",
                                                "createdBy", "updatedAt", "updatedBy"))
                                .message("Get all group achievements successful!").success(true)
                                .build().toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<GroupAchievementDto>> getGroupAchievementById(
                        @PathVariable UUID id) {
                log.info("Starting GroupAchievementController.get for id = {}", id);
                GroupAchievementDto response = groupAchievementService.getGroupAchievementById(id);
                log.info("Ending GroupAchievementController.get for id = {}", id);
                return ResponseDto.<GroupAchievementDto>builder().content(response)
                                .message(String.format(
                                                "Get group achievement with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<GroupAchievementDto>> updateGroupAchievementById(
                        @PathVariable UUID id,
                        @RequestBody @Valid GroupAchievementReq groupAchievementReq) {
                log.info("Starting GroupAchievementController.update for id = {}", id);
                GroupAchievementDto res = groupAchievementService.updateGroupAchievementById(id,
                                groupAchievementReq);
                log.info("Ending GroupAchievementController.update for id = {}", id);
                return ResponseDto.<GroupAchievementDto>builder().content(res).message(String
                                .format("Update group achievement with id %s successful!", id))
                                .success(true).build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteGroupAchievementById(@PathVariable UUID id) {
                log.info("Starting GroupAchievementController.delete for id = {}", id);
                groupAchievementService.deleteGroupAchievementById(id);
                log.info("Ending GroupAchievementController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true).message(String
                                .format("Delete group achievement with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
