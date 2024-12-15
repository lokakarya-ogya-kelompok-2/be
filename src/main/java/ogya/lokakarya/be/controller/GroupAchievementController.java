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
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementDto;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementFilter;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementReq;
import ogya.lokakarya.be.service.GroupAchievementService;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/group-achievements")
public class GroupAchievementController {
    @Autowired
    GroupAchievementService groupAchievementService;

    @PostMapping
    public ResponseEntity<ResponseDto<GroupAchievementDto>> create(
            @RequestBody @Valid GroupAchievementReq data) {
        var createdGroupAchievement = groupAchievementService.create(data);
        return ResponseDto.<GroupAchievementDto>builder().content(createdGroupAchievement)
                .message("Create group achievement successful!").success(true).build()
                .toResponse(HttpStatus.CREATED);
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
            @RequestParam(name = "with_created_by", required = false,
                    defaultValue = "false") Boolean withCreatedBy,
            @RequestParam(name = "with_updated_by", required = false,
                    defaultValue = "false") Boolean withUpdatedBy) {
        System.out.println("Get All group achievements");
        GroupAchievementFilter filter = new GroupAchievementFilter();
        filter.setNameContains(nameContains);
        filter.setMinWeight(minWeight);
        filter.setMaxWeight(maxWeight);
        filter.setEnabledOnly(enabledOnly);
        filter.setWithAchievements(withAchievements);
        filter.setWithCreatedBy(withCreatedBy);
        filter.setWithUpdatedBy(withUpdatedBy);

        List<GroupAchievementDto> response =
                groupAchievementService.getAllGroupAchievements(filter);
        System.out.println(response);
        return ResponseDto.<List<GroupAchievementDto>>builder().content(response)
                .message("Get all group achievements successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<GroupAchievementDto>> getGroupAchievementById(
            @PathVariable UUID id) {
        GroupAchievementDto response = groupAchievementService.getGroupAchievementById(id);
        return ResponseDto.<GroupAchievementDto>builder().content(response)
                .message(String.format("Get group achievement with id %s successful!", id))
                .success(true).build().toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<GroupAchievementDto>> updateGroupAchievementById(
            @PathVariable UUID id, @RequestBody @Valid GroupAchievementReq groupAchievementReq) {
        GroupAchievementDto res =
                groupAchievementService.updateGroupAchievementById(id, groupAchievementReq);
        return ResponseDto.<GroupAchievementDto>builder().content(res)
                .message(String.format("Update group achievement with id %s successful!", id))
                .success(true).build().toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteGroupAchievementById(@PathVariable UUID id) {
        groupAchievementService.deleteGroupAchievementById(id);
        return ResponseDto.<Void>builder().success(true)
                .message(String.format("Delete group achievement with id %s successful!", id))
                .build().toResponse(HttpStatus.OK);
    }
}
