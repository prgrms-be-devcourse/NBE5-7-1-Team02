package easy.gc_coffee_api.usecase.order.dto;


import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderMenuData {
    private Long orderId;
    private String menuName;
    private Integer price;
    private Integer quantity;
    private String thumbnailUrl;


    public OrderMenuData(OrderMenu orderMenu, File file) {
        this(orderMenu.orderId(), orderMenu.getName(), orderMenu.getPrice(), orderMenu.getQuantity(), getThumnailUrl(file));
    }

    private static String getThumnailUrl(File file) {
        if (file != null) {
            return file.getKey();
        }
        return null;
    }
}
