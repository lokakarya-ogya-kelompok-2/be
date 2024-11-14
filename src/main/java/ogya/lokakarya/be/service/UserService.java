package ogya.lokakarya.be.service;

import java.util.List;
import ogya.lokakarya.be.dto.user.CreateUser;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.User;

public interface UserService {
    User create(CreateUser data);

    List<UserDto> list();
}
