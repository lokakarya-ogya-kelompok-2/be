package ogya.lokakarya.be.dto.assessmentsummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SummaryData {
    private String aspect;
    private Double score = 0d;
    private Double weight = 0d;
    @JsonProperty("final_score")
    private Double finalScore = 0d;
}
