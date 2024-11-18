package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementDto;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementReq;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.service.GroupAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/group-achievements")
@RestController
public class GroupAchievementController {
    @Autowired
    GroupAchievementService groupAchievementService;

    @PostMapping
    public ResponseEntity<GroupAchievement> create(@RequestBody @Valid GroupAchievementReq data) {
        var createdGroupAchievement= groupAchievementService.create(data);
        return new ResponseEntity<>(createdGroupAchievement, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<GroupAchievementDto>> getAllGroupAchievements() {
        System.out.println("Get All Group Achievement");
        List<GroupAchievementDto> response = groupAchievementService.getAllGroupAchievements();
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GroupAchievementDto> getGroupAchievementById(@PathVariable UUID id) {
        GroupAchievementDto response = groupAchievementService.getGroupAchievementById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupAchievementDto> updateGroupAchievementById
            (@PathVariable UUID id, @RequestBody @Valid GroupAchievementReq groupAchievementReq) {
        GroupAchievementDto res= groupAchievementService.updateGroupAchievementById(id, groupAchievementReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGroupAchievementById(@PathVariable UUID id) {
        boolean res= groupAchievementService.deleteGroupAchievementById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
