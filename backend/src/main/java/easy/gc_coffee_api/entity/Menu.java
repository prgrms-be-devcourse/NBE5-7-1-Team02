package easy.gc_coffee_api.entity;

import easy.gc_coffee_api.entity.common.BaseDateEntity;
import easy.gc_coffee_api.entity.common.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String name;
    @Getter
    private Integer price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Thumbnail thumbnail;

    public Menu(Long id, String name, Integer price, Category category, Thumbnail thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.thumbnail = thumbnail;
    }

    public Menu(String name, Integer price, Category category, Thumbnail thumbnail) {
        this(null, name, price, category, thumbnail);
    }

    public void update(String name, Integer price, Category category, Thumbnail thumbnail) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.thumbnail = thumbnail;
    }

    public boolean hasThumbNail() {
        if (thumbnail == null || !thumbnail.hasId()) {
            return false;
        }
        return true;
    }

    public Long getThumbnailId() {
        if (hasThumbNail()) {
            return thumbnail.getFileId();
        }
        return null;
    }
}
