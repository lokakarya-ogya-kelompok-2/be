package ogya.lokakarya.be.dto.assessmentsummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SummaryData {
    private String aspect;
    private Integer score = 0;
    private Integer weight = 0;
    @JsonProperty("final_score")
    private Integer finalScore = 0;
}
