package ogya.lokakarya.be.dto.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.Menu;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class MenuReq {
    @NotBlank(message = "Menu name cannot be blank")
    @Size(max = 30, message = "Menu name must be less than or equal to 30 characters")
    @JsonProperty("menu_name" )
    private String menuName;

    public Menu toEntity(){
        Menu menu = new Menu();
        menu.setMenuName(menuName);
        return menu;
    }
}
