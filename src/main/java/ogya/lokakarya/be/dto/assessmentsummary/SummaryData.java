package ogya.lokakarya.be.dto.assessmentsummary;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SummaryData<T> {
    private String aspect;
    private Double score = 0d;
    private Double weight = 0d;
    @JsonProperty("final_score")
    private Double finalScore = 0d;
    private List<T> items;

}
