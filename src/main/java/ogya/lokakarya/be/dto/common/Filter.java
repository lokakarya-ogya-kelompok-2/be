package ogya.lokakarya.be.dto.common;

import org.springframework.data.domain.Sort.Direction;
import lombok.Data;

@Data
public class Filter {
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;

    private Integer pageNumber = 1;
    private Integer pageSize = 1;

    private String sortField = "createdAt";
    private Direction sortDirection = Direction.DESC;
}
