package easy.gc_coffee_api.dto;

import lombok.Getter;

@Getter
public class UpdateMenuRequestDto {
    private String name;
    private int price;
    private String category;
    private Long imageId;
}
