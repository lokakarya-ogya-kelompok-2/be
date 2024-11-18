package ogya.lokakarya.be.dto.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.Menu;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class MenuReq {
    @JsonProperty("menu_name" )
    private String menuName;

    public Menu toEntity(){
        Menu menu = new Menu();
        menu.setMenuName(menuName);
        return menu;
    }
}
