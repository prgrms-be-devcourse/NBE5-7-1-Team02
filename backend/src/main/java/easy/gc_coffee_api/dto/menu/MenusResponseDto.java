package easy.gc_coffee_api.dto.menu;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenusResponseDto {

    List<MenuResponseDto> menus = new ArrayList<>();

    public MenusResponseDto (List<MenuResponseDto> menus){
        this.menus = menus;
    }

}
