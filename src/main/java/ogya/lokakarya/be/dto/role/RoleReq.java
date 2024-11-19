package ogya.lokakarya.be.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ogya.lokakarya.be.entity.Role;

@Data
public class RoleReq {
    @NotBlank(message = "Menu name cannot be blank")
    @Size(max = 30, message = "Menu name must be less than or equal to 30 characters")
    @JsonProperty("role_name")
    private String roleName;

    public Role toEntity(){
        Role role = new Role();
        role.setRoleName(roleName);
        return role;
    }
}
