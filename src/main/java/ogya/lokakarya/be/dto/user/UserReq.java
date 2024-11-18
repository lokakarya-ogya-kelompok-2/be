package ogya.lokakarya.be.dto.user;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ogya.lokakarya.be.entity.User;

@Data
public class UserReq {
    private String username;

    @JsonProperty("full_name")
    private String fullName;

    private String position;

    @JsonProperty("employee_status")
    private Integer employeeStatus;

    private String email;

    @JsonProperty("join_date")
    private LocalDate joinDate;

    private String password;

    private Set<UUID> roles;

    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setPosition(position);
        user.setEmployeeStatus(employeeStatus);
        user.setEmailAddress(email);
        user.setJoinDate(Date.valueOf(joinDate));
        user.setPassword(password);
        return user;
    }
}
