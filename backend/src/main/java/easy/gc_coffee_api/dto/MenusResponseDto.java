package easy.gc_coffee_api.dto;

import easy.gc_coffee_api.entity.common.Category;
import lombok.*;

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
