package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.division.DivisionFilter;
import ogya.lokakarya.be.entity.Division;

public interface DivisionRepository
        extends JpaRepository<Division, UUID>, FilterRepository<Division, DivisionFilter> {
}
