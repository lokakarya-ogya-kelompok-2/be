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
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.devplan.DevPlanFilter;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;
import ogya.lokakarya.be.service.DevPlanService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/dev-plans")
public class DevPlanController {
        @Autowired
        private DevPlanService devPlanService;

        @PostMapping
        public ResponseEntity<ResponseDto<DevPlanDto>> create(@RequestBody @Valid DevPlanReq data) {
                log.info("Starting DevPlanController.create");
                var createdDevPlan = devPlanService.create(data);
                log.info("Ending DevPlanController.create");
                return ResponseDto.<DevPlanDto>builder().success(true).content(createdDevPlan)
                                .message("Create dev plan successful!").build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<DevPlanDto>>> list(
                        @RequestParam(name = "name_contains", required = false) String nameContains,
                        @RequestParam(name = "enabled_only", required = false,
                                        defaultValue = "false") Boolean enabledOnly,
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

                log.info("Starting DevPlanController.list");
                DevPlanFilter filter = new DevPlanFilter();
                filter.setNameContains(nameContains);
                filter.setEnabledOnly(enabledOnly);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                filter.setPageNumber(pageNumber);
                filter.setPageSize(pageSize);
                filter.setSortField(sortField);
                filter.setSortDirection(sortDirection);

                Page<DevPlanDto> devPlans = devPlanService.getAllDevPlans(filter);
                log.info("Ending DevPlanController.list");
                return ResponseDto.<List<DevPlanDto>>builder().success(true)
                                .content(devPlans.toList())
                                .pageInfo(new PageInfo(devPlans.getNumber() + 1, devPlans.getSize(),
                                                devPlans.getTotalPages(),
                                                devPlans.getTotalElements()))
                                .filterInfo(new FilterInfo("id", "name", "createdAt", "createdBy",
                                                "updatedAt", "updatedBy"))
                                .message("List all dev plan successful!").build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<DevPlanDto>> getById(@PathVariable UUID id) {
                log.info("Starting DevPlanController.get for id = {}", id);
                DevPlanDto devPlan = devPlanService.getDevPlanById(id);
                log.info("Ending DevPlanController.get for id = {}", id);
                return ResponseDto.<DevPlanDto>builder().success(true).content(devPlan)
                                .message(String.format("Get dev plan with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<DevPlanDto>> updateById(@PathVariable UUID id,
                        @RequestBody @Valid DevPlanReq devPlanReq) {
                log.info("Starting DevPlanController.update for id = {}", id);
                DevPlanDto updatedDevPlan = devPlanService.updateDevPlanById(id, devPlanReq);
                log.info("Ending DevPlanController.update for id = {}", id);
                return ResponseDto.<DevPlanDto>builder().success(true).content(updatedDevPlan)
                                .message(String.format("Update dev plan with id %s successful!",
                                                id))
                                .build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteById(@PathVariable UUID id) {
                log.info("Starting DevPlanController.delete for id = {}", id);
                devPlanService.deleteDevPlanById(id);
                log.info("Ending DevPlanController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true).message(
                                String.format("Delete dev plan with id %s successful!", id)).build()
                                .toResponse(HttpStatus.OK);
        }
}
