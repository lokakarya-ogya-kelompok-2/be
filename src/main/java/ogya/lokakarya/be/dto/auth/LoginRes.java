package ogya.lokakarya.be.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import ogya.lokakarya.be.dto.user.UserDto;

@Data
@AllArgsConstructor
public class LoginRes {
    private UserDto user;

    private String token;
}
