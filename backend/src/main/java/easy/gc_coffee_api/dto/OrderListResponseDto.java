package easy.gc_coffee_api.dto;

import easy.gc_coffee_api.entity.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderListResponseDto {
    private Long id;
    private String email;
    private String address;
    private String zipCode;
    private OrderStatus status;
    private Integer totalPrice;
    private List<OrderMenuResponseDto> menus;
}
