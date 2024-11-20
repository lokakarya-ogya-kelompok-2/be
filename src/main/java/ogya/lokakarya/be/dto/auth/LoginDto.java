package ogya.lokakarya.be.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDto {
    @JsonProperty("email_or_username")
    @Pattern(regexp = "^[^\\s]+$", message = "Email or Username cannot contain spaces")
    private String emailOrUsername;

    @NotBlank
    @JsonProperty("password")
    private String password;
}
