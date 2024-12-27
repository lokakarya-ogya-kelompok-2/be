package ogya.lokakarya.be.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FilterInfo {
    @JsonProperty("sortable_fields")
    private List<String> sortableFields;

    public FilterInfo(String... fieldNames) {
        sortableFields = List.of(fieldNames);
    }
}
