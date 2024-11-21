package ogya.lokakarya.be.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginReq {
    @JsonProperty("email_or_username")
    @Pattern(regexp = "^([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}|[a-zA-Z0-9._]+)$",
            message = "Please enter a valid email address or username (alphanumeric characters, dots and underscores only)")
    private String emailOrUsername;

    @NotBlank
    @JsonProperty("password")
    private String password;
}
