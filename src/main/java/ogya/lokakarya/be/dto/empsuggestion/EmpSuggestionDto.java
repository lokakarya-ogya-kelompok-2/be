package ogya.lokakarya.be.dto.empsuggestion;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.EmpSuggestion;

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
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    public EmpSuggestionDto(EmpSuggestion empSuggestion, boolean withCreatedBy, boolean withUpdatedBy) {
        setId(empSuggestion.getId());
        setUser(new UserDto(empSuggestion.getUser(), false, false, false));
        setSuggestion(empSuggestion.getSuggestion());
        setAssessmentYear(empSuggestion.getAssessmentYear());
        setCreatedAt(empSuggestion.getCreatedAt());
        if (withCreatedBy && empSuggestion.getCreatedBy() != null) {
            setCreatedBy(new UserDto(empSuggestion.getCreatedBy(), false, false, false));
        }
        setUpdatedAt(empSuggestion.getUpdatedAt());
        if (withUpdatedBy && empSuggestion.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(empSuggestion.getUpdatedBy(), false, false, false));
        }
    }
}
