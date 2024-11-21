package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.achievement.AchievementReq;
import ogya.lokakarya.be.service.AchievementService;

@RequestMapping("/achievements")
@RestController
public class AchievementController {
    @Autowired
    AchievementService achievementService;
    @Autowired
    private static final Logger LOG = LoggerFactory.getLogger(AchievementController.class);

    @PostMapping
    public ResponseEntity<ResponseDto<AchievementDto>> create(
            @RequestBody @Valid AchievementReq data) {
        LOG.info("Start method: create achievement");
        var createdAchievement = achievementService.create(data);
        LOG.info("end method: create achievement");
        return ResponseDto.<AchievementDto>builder().success(true).content(createdAchievement)
                .message("Create achievement successful!").build().toResponse(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<AchievementDto>>> getAllAchievements() {
        System.out.println("get All Achievements");
        List<AchievementDto> achievements = achievementService.getAllAchievements();
        return ResponseDto.<List<AchievementDto>>builder().success(true).content(achievements)
                .message("List all achievement successful!").build().toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<AchievementDto>> getAchievementById(@PathVariable UUID id) {
        System.out.println("get Achievement By Id");
        AchievementDto achievement = achievementService.getAchievementsById(id);
        return ResponseDto.<AchievementDto>builder().success(true).content(achievement)
                .message(String.format("Get achievement with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<AchievementDto>> updateAchievementById(@PathVariable UUID id,
            @RequestBody @Valid AchievementReq achievementReq) {
        AchievementDto updatedAchievement =
                achievementService.updateAchievementById(id, achievementReq);
        return ResponseDto.<AchievementDto>builder().success(true).content(updatedAchievement)
                .message(String.format("Update achievement with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteAchievementById(@PathVariable UUID id) {
        achievementService.deleteAchievementById(id);
        return ResponseDto.<Void>builder().success(true)
                .message(String.format("Delete achievement with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }
}
