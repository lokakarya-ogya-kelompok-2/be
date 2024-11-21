package ogya.lokakarya.be.dto.role;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.Role;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class RoleDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("role_name")
    private String roleName;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    public RoleDto(Role role, boolean withCreatedBy, boolean withUpdatedBy) {
        setId(role.getId());
        setRoleName(role.getRoleName());
        setCreatedAt(role.getCreatedAt());
        if (withCreatedBy && role.getCreatedBy() != null) {
            setCreatedBy(new UserDto(role.getCreatedBy(), false, false, false));
        }
        setUpdatedAt(role.getUpdatedAt());
        if (withUpdatedBy && role.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(role.getUpdatedBy(), false, false, false));
        }
    }

}
