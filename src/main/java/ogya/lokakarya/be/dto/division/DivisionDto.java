package ogya.lokakarya.be.dto.division;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.Division;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class DivisionDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("division_name")
    private String divisionName;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    public DivisionDto(Division division, boolean withCreatedBy, boolean withUpdatedBy) {
        setId(division.getId());
        setDivisionName(division.getName());
        setCreatedAt(division.getCreatedAt());
        if (withCreatedBy && division.getCreatedBy() != null) {
            setCreatedBy(new UserDto(division.getCreatedBy(), false, false, false));
        }
        setUpdatedAt(division.getUpdatedAt());
        if (withUpdatedBy && division.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(division.getUpdatedBy(), false, false, false));
        }
    }
}
