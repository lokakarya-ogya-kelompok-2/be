package ogya.lokakarya.be.controller.achievement;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.achievement.AchievementReq;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.service.AchievementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/achievements")
@RestController
public class AchievementController {
    @Autowired
    AchievementService achievementService;
    @Autowired
    private static final Logger LOG = LoggerFactory.getLogger(AchievementController.class);

    @PostMapping
    public ResponseEntity<Achievement> create (@RequestBody @Valid AchievementReq data) {
        LOG.info("Start method: create achievement");
        System.out.println(data);
        var createdAchievement = achievementService.create(data);
        LOG.info("end method: create achievement");
        return new ResponseEntity<>(createdAchievement, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<AchievementDto>> getAllAchievements () {
        System.out.println("get All Achievements");
        List<AchievementDto> response = achievementService.getAllAchievements();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AchievementDto> getAchievementById (@PathVariable UUID id) {
        System.out.println("get Achievement By Id");
        AchievementDto response= achievementService.getAchievementsById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AchievementDto> updateAchievementById
            (@PathVariable UUID id, @RequestBody @Valid AchievementReq achievementReq) {
        AchievementDto res= achievementService.updateAchievementById(id, achievementReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAchievementById (@PathVariable UUID id) {
    boolean res= achievementService.deleteAchievementById(id);
    return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
