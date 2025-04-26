package easy.gc_coffee_api.dto;

import easy.gc_coffee_api.entity.Orders;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    Long orderId;

    public OrderResponseDto(Long orderId) {
        this.orderId = orderId;
    }

    public static OrderResponseDto fromEntity(Orders savedOrder) {
        return new OrderResponseDto(savedOrder.getId());
    }
}
