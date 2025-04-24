package easy.gc_coffee_api.entity;

import easy.gc_coffee_api.entity.common.BaseDateEntity;
import easy.gc_coffee_api.entity.common.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Menu extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Thumnail thumnail;

    public Menu(Long id, String name, Integer price, Category category, Thumnail thumnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.thumnail = thumnail;
    }

    public Menu(String name, Integer price, Category category, Thumnail thumbnail) {
        this(null, name, price, category, thumbnail);
    }

    public void update(String name, Integer price, Category category, Thumnail thumbnail) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.thumnail = thumbnail;
    }

}
