package ogya.lokakarya.be.dto.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@EmailOrUsername
public class LoginDto {
    @Email
    @JsonProperty("email")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9._]+$",
            message = "Username can only contain alphanumeric characters, underscores, and dots")
    @JsonProperty("username")
    private String username;

    @NotBlank
    @JsonProperty("password")
    private String password;
}


class EmailOrUsernameValidator implements ConstraintValidator<EmailOrUsername, LoginDto> {

    @Override
    public void initialize(EmailOrUsername constraintAnnotation) {}

    @Override
    public boolean isValid(LoginDto loginDto, ConstraintValidatorContext context) {
        return ((loginDto.getEmail() != null && !loginDto.getEmail().isEmpty())
                && (loginDto.getUsername() == null || loginDto.getUsername().isEmpty())
                || (loginDto.getUsername() != null && !loginDto.getUsername().isEmpty())
                        && (loginDto.getEmail() == null || loginDto.getEmail().isEmpty()));
    }
}


@Constraint(validatedBy = EmailOrUsernameValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface EmailOrUsername {
    String message()

    default "Either one of email or username must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
