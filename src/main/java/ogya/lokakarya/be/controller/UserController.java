package ogya.lokakarya.be.controller;

import java.time.LocalDate;
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
import ogya.lokakarya.be.dto.user.UserChangePasswordDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.dto.user.UserFilter;
import ogya.lokakarya.be.dto.user.UserReq;
import ogya.lokakarya.be.dto.user.UserUpdateDto;
import ogya.lokakarya.be.service.UserService;



@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/users")
public class UserController {
        @Autowired
        private UserService userSvc;


        @PostMapping
        public ResponseEntity<ResponseDto<UserDto>> create(@RequestBody @Valid UserReq data) {
                log.info("Starting UserController.create");
                var createdUser = userSvc.create(data);
                log.info("Ending UserController.create");
                return ResponseDto.<UserDto>builder().success(true).content(createdUser)
                                .message("Create user successful!").build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<UserDto>>> list(
                        @RequestParam(name = "any_contains",
                                        required = false) String anyStringFieldsContains,
                        @RequestParam(name = "page_number", required = false) Integer pageNumber,
                        @RequestParam(name = "page_size", required = false,
                                        defaultValue = "5") Integer pageSize,
                        @RequestParam(name = "username_contains",
                                        required = false) String usernameContains,
                        @RequestParam(name = "name_contains", required = false) String nameContains,
                        @RequestParam(name = "position_contains",
                                        required = false) String positionContains,
                        @RequestParam(name = "email_contains",
                                        required = false) String emailContains,
                        @RequestParam(name = "min_join_date",
                                        required = false) LocalDate minJoinDate,
                        @RequestParam(name = "max_join_date",
                                        required = false) LocalDate maxJoinDate,
                        @RequestParam(name = "employee_status",
                                        required = false) Integer employeeStatus,
                        @RequestParam(name = "division_name_contains",
                                        required = false) String divisionName,
                        @RequestParam(name = "enabled_only", required = false,
                                        defaultValue = "false") Boolean enabledOnly,
                        @RequestParam(name = "with_roles", required = false,
                                        defaultValue = "false") Boolean withRoles,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy) {
                log.info("Starting UserController.list");
                UserFilter filter = new UserFilter();
                filter.setAnyStringFieldsContains(anyStringFieldsContains);
                filter.setPageNumber(pageNumber);
                filter.setPageSize(pageSize);
                filter.setUsernameContains(usernameContains);
                filter.setNameContains(nameContains);
                filter.setPositionContains(positionContains);
                filter.setEmailContains(emailContains);
                filter.setMinJoinDate(minJoinDate);
                filter.setMaxJoinDate(maxJoinDate);
                filter.setEmployeeStatus(employeeStatus);
                filter.setDivisionNameContains(divisionName);
                filter.setEnabledOnly(enabledOnly);
                filter.setWithRoles(withRoles);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);

                Page<UserDto> users = userSvc.list(filter);
                log.info("Ending UserController.list");
                return ResponseDto.<List<UserDto>>builder().success(true).content(users.toList())
                                .totalPages(users.getTotalPages())
                                .totalRecords(users.getTotalElements())
                                .pageNumber(users.getNumber()).pageSize(users.getSize())
                                .message("List users successful!").build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<UserDto>> get(@PathVariable UUID id) {
                log.info("Starting UserController.get for id = {}", id);
                var user = userSvc.get(id);
                log.info("Ending UserController.get for id = {}", id);
                return ResponseDto.<UserDto>builder().success(true).content(user)
                                .message(String.format("Get user with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<UserDto>> update(@PathVariable UUID id,
                        @RequestBody @Valid UserUpdateDto data) {
                log.info("Starting UserController.update for id = {}", id);
                var updatedUser = userSvc.update(id, data);
                log.info("Ending UserController.update for id = {}", id);
                return ResponseDto.<UserDto>builder().success(true).content(updatedUser)
                                .message(String.format("Update user with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> delete(@PathVariable UUID id) {
                log.info("Starting UserController.delete for id = {}", id);
                userSvc.delete(id);
                log.info("Ending UserController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true)
                                .message(String.format("Delete user with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/change-password")
        public ResponseEntity<ResponseDto<UserDto>> changePassword(
                        @RequestBody @Valid UserChangePasswordDto data) {
                log.info("Starting UserController.changePassword");
                var updatedUser = userSvc.changePassword(data);
                log.info("Ending UserController.changePassword");
                return ResponseDto.<UserDto>builder().success(true).content(updatedUser)
                                .message("Password changed successfuly!").build()
                                .toResponse(HttpStatus.OK);
        }

        @PostMapping("/{id}/reset-password")
        public ResponseEntity<ResponseDto<String>> resetPassword(@PathVariable UUID id) {
                log.info("Starting UserController.resetPassword for id = {}", id);
                var generatedPassword = userSvc.resetPassword(id);
                log.info("Ending UserController.resetPassword for id = {}", id);
                return ResponseDto.<String>builder().success(true).content(generatedPassword)
                                .message("Reset password successful!").build()
                                .toResponse(HttpStatus.OK);
        }

}
