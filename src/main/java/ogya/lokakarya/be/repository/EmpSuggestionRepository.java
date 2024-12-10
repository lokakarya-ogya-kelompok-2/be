package ogya.lokakarya.be.repository;

import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionFilter;
import ogya.lokakarya.be.entity.EmpSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmpSuggestionRepository extends JpaRepository<EmpSuggestion, UUID>,
        FilterRepository<EmpSuggestion, EmpSuggestionFilter> {
}
