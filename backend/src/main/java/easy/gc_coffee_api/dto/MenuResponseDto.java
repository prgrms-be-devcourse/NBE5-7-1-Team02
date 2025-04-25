package easy.gc_coffee_api.dto;

import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.Menu;
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
    public MenuResponseDto(Long id, String name, int price, Category category, File file){
        this(id, name, price, category, getUrl(file), getFileId(file));
    }

    private static String getUrl(File file) {
        if (file == null) {
            return null;
        }
        // 임의로 지정하였습니다. -> 추후 수정 필요합니다.
        String prefix = "http://localhost:8084/";
        return prefix + file.getKey();
    }

    private static Long getFileId(File file) {
        if (file == null) {
            return null;
        }
        return file.getId();
    }

}
