package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.dto.user.UserReq;

public interface UserService {
    UserDto create(UserReq data);

    List<UserDto> list();

    UserDto get(UUID id);

    UserDto update(UUID id, UserReq data);

    void delete(UUID id);
}
