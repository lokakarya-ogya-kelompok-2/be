package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ogya.lokakarya.be.entity.Division;

public interface DivisionRepository
                extends JpaRepository<Division, UUID>, JpaSpecificationExecutor<Division> {
}
