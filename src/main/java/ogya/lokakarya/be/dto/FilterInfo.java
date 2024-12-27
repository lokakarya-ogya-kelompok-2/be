package ogya.lokakarya.be.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterInfo {
    @JsonProperty("sortable_fields")
    private List<String> sortableFields;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("searchable_fields")
    private List<String> searchableFields;

    public FilterInfo(String... fieldNames) {
        sortableFields = List.of(fieldNames);
    }
}
