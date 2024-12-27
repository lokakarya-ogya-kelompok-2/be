package ogya.lokakarya.be.dto.user;

import java.time.LocalDate;
import java.util.Set;
import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.dto.common.Filter;
import ogya.lokakarya.be.exception.ResponseException;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserFilter extends Filter {
    // ini kaya gabungan OR antara username, name, position, email, divisionName. males mikir, gini
    // aja wkwk
    private String anyStringFieldsContains;
    private Set<String> searchBy;
    private String usernameContains;
    private String nameContains;
    private String positionContains;
    private String emailContains;
    private LocalDate minJoinDate;
    private LocalDate maxJoinDate;
    private Integer employeeStatus;
    private String divisionNameContains;
    private Boolean enabledOnly = false;
    private Boolean withRoles = false;

    public void validate() throws ResponseException {
        if (maxJoinDate != null && minJoinDate != null && maxJoinDate.isAfter(minJoinDate)) {
            throw new ResponseException("max_join_date can't be less than min_join_date!",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
