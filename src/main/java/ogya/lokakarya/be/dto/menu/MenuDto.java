package ogya.lokakarya.be.dto.menu;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.Menu;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class MenuDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("menu_name")
    private String menuName;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    public MenuDto(Menu menu, boolean withCreatedBy, boolean withUpdatedBy) {
        setId(menu.getId());
        setMenuName(menu.getMenuName());
        setCreatedAt(menu.getCreatedAt());
        if (withCreatedBy && menu.getCreatedBy() != null) {
            setCreatedBy(new UserDto(menu.getCreatedBy(), false, false, false));
        }
        setUpdatedAt(menu.getUpdatedAt());
        if (withUpdatedBy && menu.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(menu.getUpdatedBy(), false, false, false));
        }
    }
}
