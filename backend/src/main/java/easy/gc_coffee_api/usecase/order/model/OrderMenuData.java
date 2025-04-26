package easy.gc_coffee_api.usecase.order.model;


import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderMenuData {
    private Long orderId;
    private Long menuId;
    private String menuName;
    private Integer price;
    private Integer quantity;
    private String thumbnailUrl;


    public OrderMenuData(OrderMenu orderMenu, File file) {
        this(orderMenu.orderId(), orderMenu.getMenu().getId(), orderMenu.getName(), orderMenu.getPrice(), orderMenu.getQuantity(), getThumbnailUrl(file));
    }

    private static String getThumbnailUrl(File file) {
        if (file != null) {
            return file.getKey();
        }
        return null;
    }

    public boolean hasThumbnailUrl() {
        return thumbnailUrl != null;
    }
}
