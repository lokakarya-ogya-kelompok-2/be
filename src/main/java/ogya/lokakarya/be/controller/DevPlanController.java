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
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.devplan.DevPlanFilter;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;
import ogya.lokakarya.be.service.DevPlanService;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/dev-plans")
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
    public ResponseEntity<ResponseDto<List<DevPlanDto>>> list(
            @RequestParam(name = "name_contains", required = false) String nameContains,
            @RequestParam(name = "enabled_only", required = false,
                    defaultValue = "false") Boolean enabledOnly,
            @RequestParam(name = "with_created_by", required = false,
                    defaultValue = "false") Boolean withCreatedBy,
            @RequestParam(name = "with_updated_by", required = false,
                    defaultValue = "false") Boolean withUpdatedBy) {
        DevPlanFilter filter = new DevPlanFilter();
        filter.setNameContains(nameContains);
        filter.setEnabledOnly(enabledOnly);
        filter.setWithCreatedBy(withCreatedBy);
        filter.setWithUpdatedBy(withUpdatedBy);
        List<DevPlanDto> devPlans = devPlanService.getAllDevPlans(filter);
        return ResponseDto.<List<DevPlanDto>>builder().success(true).content(devPlans)
                .message("List all dev plan successful!").build().toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<DevPlanDto>> getById(@PathVariable UUID id) {
        DevPlanDto devPlan = devPlanService.getDevPlanById(id);
        return ResponseDto.<DevPlanDto>builder().success(true).content(devPlan)
                .message(String.format("Get dev plan with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<DevPlanDto>> updateById(@PathVariable UUID id,
            @RequestBody @Valid DevPlanReq devPlanReq) {
        DevPlanDto updatedDevPlan = devPlanService.updateDevPlanById(id, devPlanReq);
        return ResponseDto.<DevPlanDto>builder().success(true).content(updatedDevPlan)
                .message(String.format("Update dev plan with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteById(@PathVariable UUID id) {
        devPlanService.deleteDevPlanById(id);
        return ResponseDto.<Void>builder().success(true)
                .message(String.format("Delete dev plan with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }
}
