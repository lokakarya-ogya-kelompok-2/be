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
import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.achievement.AchievementFilter;
import ogya.lokakarya.be.dto.achievement.AchievementReq;
import ogya.lokakarya.be.service.AchievementService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/achievements")
public class AchievementController {
        @Autowired
        AchievementService achievementService;

        @PostMapping
        public ResponseEntity<ResponseDto<AchievementDto>> create(
                        @RequestBody @Valid AchievementReq data) {
                log.info("Starting AchievementController.create");
                var createdAchievement = achievementService.create(data);
                log.info("Ending AchievementController.create");
                return ResponseDto.<AchievementDto>builder().success(true)
                                .content(createdAchievement)
                                .message("Create achievement successful!").build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<AchievementDto>>> getAllAchievements(
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
                                        defaultValue = "5") Integer pageSize) {
                log.info("Starting AchievementController.list");
                AchievementFilter filter = new AchievementFilter();
                filter.setAnyStringFieldContains(anyStringFieldContains);
                filter.setNameContains(nameContains);
                filter.setGroupIds(groupIds);
                filter.setEnabledOnly(enabledOnly);
                filter.setWithGroup(withGroup);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                filter.setPageNumber(pageNumber);
                filter.setPageSize(pageSize);

                Page<AchievementDto> achievements = achievementService.getAllAchievements(filter);
                log.info("Ending AchievementController.list");
                return ResponseDto.<List<AchievementDto>>builder().success(true)
                                .content(achievements.toList())
                                .totalRecords(achievements.getTotalElements())
                                .totalPages(achievements.getTotalPages())
                                .pageNumber(achievements.getNumber() + 1)
                                .pageSize(achievements.getSize())
                                .message("List all achievement successful!").build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<AchievementDto>> getAchievementById(
                        @PathVariable UUID id) {
                log.info("Starting AchievementController.get for id = {}", id);
                AchievementDto achievement = achievementService.getAchievementsById(id);
                log.info("Ending AchievementController.get for id = {}", id);
                return ResponseDto.<AchievementDto>builder().success(true).content(achievement)
                                .message(String.format("Get achievement with id %s successful!",
                                                id))
                                .build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<AchievementDto>> updateAchievementById(
                        @PathVariable UUID id, @RequestBody @Valid AchievementReq achievementReq) {
                log.info("Starting AchievementController.update for id = {}", id);
                AchievementDto updatedAchievement =
                                achievementService.updateAchievementById(id, achievementReq);
                log.info("Ending AchievementController.update for id = {}");
                return ResponseDto.<AchievementDto>builder().success(true)
                                .content(updatedAchievement)
                                .message(String.format("Update achievement with id %s successful!",
                                                id))
                                .build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteAchievementById(@PathVariable UUID id) {
                log.info("Starting AchievementController.delete for id = {}", id);
                achievementService.deleteAchievementById(id);
                log.info("Ending AchievementController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true).message(
                                String.format("Delete achievement with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }
}
