package easy.gc_coffee_api.usecase.menu.dto;

import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.common.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MenuData {
    private Long id;
    private String name;
    private int price;
    private Category category;
    private Long fileId;
    private String thumbnailUrl;
    @Setter
    private String fullPathUrl;

    public MenuData(Long id, String name, int price, Category category, Long fileId, String thumbnailUrl, String fullPathUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.fileId = fileId;
        this.thumbnailUrl = thumbnailUrl;
        this.fullPathUrl = fullPathUrl;
    }

    public MenuData(Long id, String name, int price, Category category, Long fileId, String thumbnailUrl) {
        this(id, name, price, category, fileId, thumbnailUrl, null);
    }


    public MenuData(Menu menu, File file) {
        this(menu.getId(), menu.getName(), menu.getPrice(), menu.getCategory(), getFileId(file), getUrl(file));
    }

    private static String getUrl(File file) {
        if (file == null) {
            return null;
        }
        return file.getKey();
    }

    private static Long getFileId(File file) {
        if (file == null) {
            return null;
        }
        return file.getId();
    }

    public boolean hasThumbnail() {
        if (fileId == null && thumbnailUrl == null) {
            return false;
        }
        return true;
    }
}
