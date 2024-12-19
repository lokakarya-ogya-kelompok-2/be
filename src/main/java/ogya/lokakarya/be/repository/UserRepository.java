package ogya.lokakarya.be.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ogya.lokakarya.be.dto.user.UserFilter;
import ogya.lokakarya.be.entity.User;

@Repository
public interface UserRepository
        extends JpaRepository<User, UUID>, FilterRepository<User, UserFilter> {
    Optional<User> findByEmailAddressIgnoreCase(String email);

    Optional<User> findByUsername(String username);
}
