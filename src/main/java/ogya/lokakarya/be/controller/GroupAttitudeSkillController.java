package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillReq;
import ogya.lokakarya.be.service.GroupAttitudeSkillService;
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

import java.util.List;
import java.util.UUID;

@RequestMapping("/group-attitude-skills")
@RestController
public class GroupAttitudeSkillController {
    @Autowired
    GroupAttitudeSkillService groupAttitudeSkillService;

    @PostMapping
    public ResponseEntity<ResponseDto<GroupAttitudeSkillDto>> create(@RequestBody @Valid GroupAttitudeSkillReq data) {
        var createdGroupAttitudeSill= groupAttitudeSkillService.create(data);
        return ResponseDto.<GroupAttitudeSkillDto>builder().content(createdGroupAttitudeSill)
                .message("Create group attitude skill successful!").success(true).build()
                .toResponse(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ResponseDto<List<GroupAttitudeSkillDto>>> getAllGroupAttitudeSkills() {
        System.out.println("Get All Group Attitude Skills");
        List<GroupAttitudeSkillDto> response = groupAttitudeSkillService.getAllGroupAttitudeSkills();
        return ResponseDto.<List<GroupAttitudeSkillDto>>builder().content(response)
                .message("Get all group attitude skill successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<GroupAttitudeSkillDto>> getGroupAttitudeSkillById(@PathVariable UUID id) {
        GroupAttitudeSkillDto response = groupAttitudeSkillService.getGroupAttitudeSkillById(id);
        return ResponseDto.<GroupAttitudeSkillDto>builder().content(response)
                .message(String.format("Get group attitude skill with id %s successful!", id)).success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<GroupAttitudeSkillDto>> updateDivisionById
            (@PathVariable UUID id, @RequestBody @Valid GroupAttitudeSkillReq groupAttitudeSkillReq) {
        GroupAttitudeSkillDto res= groupAttitudeSkillService.updateGroupAttitudeSkillById(id, groupAttitudeSkillReq);
        return ResponseDto.<GroupAttitudeSkillDto>builder().content(res)
                .message(String.format("Update group attitude skill with id %s successful!", id)).success(true)
                .build().toResponse(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteGroupAttitudeById(@PathVariable UUID id) {
        groupAttitudeSkillService.deleteGroupAttitudeSkillById(id);
        return ResponseDto.<Void>builder().success(true)
                .message(String.format("Delete group attitude skill with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }
}
