package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.user.UserChangePasswordDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.dto.user.UserReq;
import ogya.lokakarya.be.dto.user.UserUpdateDto;

public interface UserService {
    UserDto create(UserReq data);

    List<UserDto> list();

    UserDto get(UUID id);

    UserDto update(UUID id, UserUpdateDto data);

    void delete(UUID id);

    UserDto changePassword(UserChangePasswordDto data);

    String resetPassword(UUID userId);
}
