package easy.gc_coffee_api.usecase.order.model;


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

    public OrderMenuData(OrderMenu orderMenu) {
        this(orderMenu.orderId(), orderMenu.getMenu().getId(), orderMenu.getName(), orderMenu.getPrice(), orderMenu.getQuantity());
    }

}
