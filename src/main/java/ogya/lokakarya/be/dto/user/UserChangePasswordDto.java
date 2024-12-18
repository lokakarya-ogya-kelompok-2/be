package ogya.lokakarya.be.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserChangePasswordDto {
    @NotBlank
    @Size(min = 8, max = 32)
    @JsonProperty("current_password")
    private String currentPassword;

    @NotBlank
    @Size(min = 8, max = 32)
    @JsonProperty("new_password")
    private String newPassword;

    @NotBlank
    @Size(min = 8, max = 32)
    @JsonProperty("confirm_new_password")
    private String confirmNewPassword;
}
