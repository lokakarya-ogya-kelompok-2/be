package ogya.lokakarya.be.dto.empsuggestion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.EmpSuggestion;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpSuggestionReq {
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
