package ogya.lokakarya.be.dto.user;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ogya.lokakarya.be.entity.User;

@Data
public class UserUpdateDto {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9._]+$",
            message = "Username can only contain alphanumeric characters, underscores, and dots")
    private String username;

    @NotEmpty
    @JsonProperty("full_name")
    private String fullName;

    @NotEmpty
    private String position;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @JsonProperty("employee_status")
    private Integer employeeStatus;

    @NotEmpty
    @Email
    private String email;

    @NotNull
    @JsonProperty("join_date")
    private LocalDate joinDate;

    @NotNull
    @JsonProperty("division_id")
    private UUID divisionId;

    @NotNull
    @Size(min = 1, message = "can't be empty")
    private Set<UUID> roles;

    @NotNull
    private Boolean enabled;

    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setPosition(position);
        user.setEmployeeStatus(employeeStatus);
        user.setEmailAddress(email);
        user.setEnabled(enabled);
        user.setJoinDate(Date.valueOf(joinDate));
        return user;
    }
}
