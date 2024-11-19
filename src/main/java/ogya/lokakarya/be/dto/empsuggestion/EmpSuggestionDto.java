package ogya.lokakarya.be.dto.empsuggestion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.EmpSuggestion;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpSuggestionDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("user_id")
    private UserDto user;

    @JsonProperty("suggestion")
    private String suggestion;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public EmpSuggestionDto(EmpSuggestion empSuggestion) {
        setId(empSuggestion.getId());
        setUser(new UserDto(empSuggestion.getUser(), false, false, false));
        setSuggestion(empSuggestion.getSuggestion());
        setAssessmentYear(empSuggestion.getAssessmentYear());
        setCreatedAt(empSuggestion.getCreatedAt());
        setCreatedBy(empSuggestion.getCreatedBy());
        setUpdatedAt(empSuggestion.getUpdatedAt());
        setUpdatedBy(empSuggestion.getUpdatedBy());
    }
}
