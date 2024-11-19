package ogya.lokakarya.be.dto.empsuggestion;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("user_id")
    private UUID user;

    @JsonProperty("suggestion")
    private String suggestion;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpSuggestion toEntity(){
        EmpSuggestion empSuggestion = new EmpSuggestion();
        empSuggestion.setSuggestion(suggestion);
        empSuggestion.setAssessmentYear(assessmentYear);
        return empSuggestion;
    }
}
