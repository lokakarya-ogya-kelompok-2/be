package ogya.lokakarya.be.dto.division;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.Division;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class DivisionReq {
    @JsonProperty("division_name")
    private String divisionName;

    public Division toEntity(){
        Division division  = new Division();
        division.setDivisionName(divisionName);
        return division;
    }
}
