package ogya.lokakarya.be.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.Role;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

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
    private Date createdAt = Date.valueOf(LocalDate.now());

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public RoleDto(Role role) {
        setId(role.getId());
        setRoleName(role.getRoleName());
        setCreatedAt(role.getCreatedAt());
        setCreatedBy(role.getCreatedBy());
        setUpdatedAt(role.getUpdatedAt());
        setUpdatedBy(role.getUpdatedBy());
    }

}
