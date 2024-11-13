package ogya.lokakarya.be.dto.user;

import java.sql.Date;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ogya.lokakarya.be.entity.User;

@Data
public class CreateUser {
    private String username;
    @JsonProperty("full_name")
    private String fullName;
    private String position;
    private String email;
    @JsonProperty("join_date")
    private LocalDate joinDate;
    private String password;

    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setPosition(position);
        user.setEmailAddress(email);
        user.setJoinDate(Date.valueOf(joinDate));
        user.setPassword(password);
        return user;
    }
}
