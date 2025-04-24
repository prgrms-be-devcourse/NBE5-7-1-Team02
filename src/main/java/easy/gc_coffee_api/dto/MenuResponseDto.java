package easy.gc_coffee_api.dto;

import easy.gc_coffee_api.entity.common.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuResponseDto {
    private String name;
    private int price;
    private Category category;
    private String thumbnailUrl;

    @Builder
    public MenuResponseDto(String name, int price, Category category, String thumbnailUrl) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
    }
}
