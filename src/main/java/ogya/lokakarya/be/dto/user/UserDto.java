package ogya.lokakarya.be.dto.user;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ogya.lokakarya.be.entity.User;

@Data
public class UserDto {
    private UUID id;

    private String username;

    @JsonProperty("full_name")
    private String fullName;

    private String position;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("join_date")
    private LocalDate joinDate;

    private Boolean enabled;

    private String password;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    public UserDto(User user, boolean withCreatedBy, boolean withUpdatedBy) {
        setId(getId());
        setUsername(user.getUsername());
        setFullName(user.getFullName());
        setPosition(user.getPosition());
        setEmailAddress(user.getEmailAddress());
        setJoinDate(user.getJoinDate().toLocalDate());
        setEnabled(user.getEnabled());
        if (user.getCreatedAt() != null) {
            setCreatedAt(user.getCreatedAt());
        }
        if (withCreatedBy && user.getCreatedBy() != null) {
            setCreatedBy(new UserDto(user.getCreatedBy(), false, false));
        }
        if (user.getUpdatedAt() != null) {
            setUpdatedAt(user.getUpdatedAt());
        }

        if (withUpdatedBy && user.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(user.getUpdatedBy(), false, false));
        }
    }
}
