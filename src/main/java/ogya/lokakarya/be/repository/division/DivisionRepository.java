package ogya.lokakarya.be.repository.division;

import ogya.lokakarya.be.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DivisionRepository extends JpaRepository <Division, UUID>{
}
