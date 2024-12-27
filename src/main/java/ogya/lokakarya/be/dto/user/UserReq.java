package ogya.lokakarya.be.dto.user;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ogya.lokakarya.be.entity.User;

@Data
public class UserReq {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._]+$",
            message = "Username can only contain alphanumeric characters, underscores, and dots")
    private String username;

    @NotBlank
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank
    private String position;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @JsonProperty("employee_status")
    private Integer employeeStatus;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @JsonProperty("join_date")
    private LocalDate joinDate;

    @NotNull
    @JsonProperty("division_id")
    private UUID divisionId;

    @NotBlank
    @Size(min = 8, max = 32)
    private String password;

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
        user.setEmail(email);
        user.setEnabled(enabled);
        user.setJoinDate(Date.valueOf(joinDate));
        user.setPassword(password);
        return user;
    }
}
