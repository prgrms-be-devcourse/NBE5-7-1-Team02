package easy.gc_coffee_api.dto.order;

import easy.gc_coffee_api.usecase.order.model.OrderMenuModel;
import easy.gc_coffee_api.entity.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private String email;
    private String address;
    private String zipCode;
    private OrderStatus status;
    private Integer totalPrice;
    private List<OrderMenuModel> menus;
}
