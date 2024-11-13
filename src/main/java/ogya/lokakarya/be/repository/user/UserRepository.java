package ogya.lokakarya.be.repository.user;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ogya.lokakarya.be.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
