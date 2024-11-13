package ogya.lokakarya.be.service.user;

import ogya.lokakarya.be.dto.user.Create;
import ogya.lokakarya.be.entity.User;

public interface UserService {
    User create(Create data);
}
