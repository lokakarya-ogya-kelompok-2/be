package ogya.lokakarya.be.dto.empsuggestion;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.EmpSuggestion;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpSuggestionReq {
    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    private UUID user;

    @NotBlank(message = "Suggestion cannot be blank")
    @Size(max = 200, message = "Suggestion must be less than or equal to 200 characters")
    @JsonProperty("suggestion")
    private String suggestion;

    @NotNull(message = "Assessment year cannot be null")
    @Min(value = 1900, message = "Assessment year must be greater than or equal to 1900")
    @Max(value = 2100, message = "Assessment year must be less than or equal to 2100")
    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpSuggestion toEntity(){
        EmpSuggestion empSuggestion = new EmpSuggestion();
        empSuggestion.setSuggestion(suggestion);
        empSuggestion.setAssessmentYear(assessmentYear);
        return empSuggestion;
    }
}
