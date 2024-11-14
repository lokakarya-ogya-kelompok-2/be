package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.auth.LoginDto;

public interface AuthService {
    String login(LoginDto data);
}
