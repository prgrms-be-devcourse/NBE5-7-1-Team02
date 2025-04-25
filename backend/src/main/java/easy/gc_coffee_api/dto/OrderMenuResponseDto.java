package easy.gc_coffee_api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderMenuResponseDto {
    private String menuName;
    private Integer price;
    private Integer quantity;
    private String thumbnailUrl;

}
