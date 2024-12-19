package ogya.lokakarya.be.dto.user;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import lombok.Data;
import ogya.lokakarya.be.exception.ResponseException;

@Data
public class UserFilter {
    // ini kaya gabungan OR antara username, name, position, email, divisionName. males mikir, gini
    // aja wkwk
    private String anyStringFieldsContains;
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
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
    private Integer pageNumber;
    private Integer pageSize;

    public void validate() throws ResponseException {
        if (maxJoinDate != null && minJoinDate != null && maxJoinDate.isAfter(minJoinDate)) {
            throw new ResponseException("max_join_date can't be less than min_join_date!",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
