package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.user.CreateUserDto;
import ogya.lokakarya.be.dto.user.UserDto;

public interface UserService {
    UserDto create(CreateUserDto data);

    List<UserDto> list();

    UserDto get(UUID id);
}
