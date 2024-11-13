package ogya.lokakarya.be.dto.Role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ogya.lokakarya.be.entity.Role;

@Data
public class CreateRole {
    @JsonProperty("role_name")
    private String roleName;

    public Role toEntity(){
        Role role = new Role();
        role.setRoleName(roleName);
        return role;
    }
}
