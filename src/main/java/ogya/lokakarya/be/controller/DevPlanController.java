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
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;
import ogya.lokakarya.be.service.DevPlanService;

@RequestMapping("/dev-plans")
@RestController
public class DevPlanController {
    @Autowired
    private DevPlanService devPlanService;

    @PostMapping
    public ResponseEntity<ResponseDto<DevPlanDto>> create(@RequestBody @Valid DevPlanReq data) {
        var createdDevPlan = devPlanService.create(data);
        return ResponseDto.<DevPlanDto>builder().success(true).content(createdDevPlan)
                .message("Create dev plan successful!").build().toResponse(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<DevPlanDto>>> getAllDivisions() {
        List<DevPlanDto> devPlans = devPlanService.getAllDevPlans();
        return ResponseDto.<List<DevPlanDto>>builder().success(true).content(devPlans)
                .message("List all dev plan successful!").build().toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<DevPlanDto>> getDivisionById(@PathVariable UUID id) {
        DevPlanDto devPlan = devPlanService.getDevPlanById(id);
        return ResponseDto.<DevPlanDto>builder().success(true).content(devPlan)
                .message(String.format("Get dev plan with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<DevPlanDto>> updateDivisionById(@PathVariable UUID id,
            @RequestBody @Valid DevPlanReq devPlanReq) {
        DevPlanDto updatedDevPlan = devPlanService.updateDevPlanById(id, devPlanReq);
        return ResponseDto.<DevPlanDto>builder().success(true).content(updatedDevPlan)
                .message(String.format("Update dev plan with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteDivisionById(@PathVariable UUID id) {
        devPlanService.deleteDevPlanById(id);
        return ResponseDto.<Void>builder().success(true)
                .message(String.format("Delete dev plan with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }
}
