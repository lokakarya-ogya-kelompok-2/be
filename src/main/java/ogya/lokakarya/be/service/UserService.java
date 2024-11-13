package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.user.CreateUser;
import ogya.lokakarya.be.entity.User;

public interface UserService {
    User create(CreateUser data);
}
