package ogya.lokakarya.be.dto.Role;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ogya.lokakarya.be.entity.Role;

@Data
public class RoleDto {
    private UUID id;

    @JsonProperty("role_name")
    private String roleName;

    public RoleDto(Role data) {
        id = data.getId();
        roleName = data.getRoleName();
    }
}
