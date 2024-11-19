package ogya.lokakarya.be.dto.division;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.Division;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class DivisionReq {
    @NotBlank(message = "Menu name cannot be blank")
    @Size(max = 50, message = "Menu name must be less than or equal to 50 characters")
    @JsonProperty("division_name")
    private String divisionName;

    public Division toEntity(){
        Division division  = new Division();
        division.setDivisionName(divisionName);
        return division;
    }
}
