package ogya.lokakarya.be.dto.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.Menu;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class MenuDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("menu_name" )
    private String menuName;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public MenuDto(Menu menu){
        setId(menu.getId());
        setMenuName(menu.getMenuName());
        setCreatedAt(menu.getCreatedAt());
        setCreatedBy(menu.getCreatedBy());
        setUpdatedAt(menu.getUpdatedAt());
        setUpdatedBy(menu.getUpdatedBy());
    }
}
