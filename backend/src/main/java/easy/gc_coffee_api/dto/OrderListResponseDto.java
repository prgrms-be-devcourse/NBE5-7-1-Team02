package easy.gc_coffee_api.dto;


import easy.gc_coffee_api.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderListResponseDto {
    private Long orderId;
    private String email;
    private Address address;
    private Integer totalPrice;
    private List<OrderMenuResponseDto> orderMenus;
}
