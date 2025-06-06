package easy.gc_coffee_api.dto.menu;

import easy.gc_coffee_api.entity.common.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private Category category;
    private Long fileId;
    private String thumbnailUrl;

    @Builder
    public MenuResponseDto(Long id, String name, int price, Category category, String thumbnailUrl, Long fileId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.fileId = fileId;
        this.thumbnailUrl = thumbnailUrl;
    }

}
