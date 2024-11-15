package ogya.lokakarya.be.repository.empsuggestion;

import ogya.lokakarya.be.entity.EmpSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmpSuggestionRepository extends JpaRepository<EmpSuggestion, UUID> {
}
