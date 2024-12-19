package ogya.lokakarya.be.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import ogya.lokakarya.be.dto.user.UserChangePasswordDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.dto.user.UserFilter;
import ogya.lokakarya.be.dto.user.UserReq;
import ogya.lokakarya.be.dto.user.UserUpdateDto;

public interface UserService {
    UserDto create(UserReq data);

    Page<UserDto> list(UserFilter filter);

    UserDto get(UUID id);

    UserDto update(UUID id, UserUpdateDto data);

    void delete(UUID id);

    UserDto changePassword(UserChangePasswordDto data);

    String resetPassword(UUID userId);
}
