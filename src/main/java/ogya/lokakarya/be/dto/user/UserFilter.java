package ogya.lokakarya.be.dto.user;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserFilter {
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
}
