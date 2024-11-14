package ogya.lokakarya.be.dto.division;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.Division;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

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

    @JsonProperty("created_add")
    private Date createdAt = Date.valueOf(LocalDate.now());

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public DivisionDto(Division division) {
        setId(division.getId());
        setDivisionName(division.getDivisionName());
        setCreatedAt(division.getCreatedAt());
        setCreatedBy(division.getCreatedBy());
        setUpdatedAt(division.getUpdatedAt());
        setUpdatedBy(division.getUpdatedBy());
    }
}
