package easy.gc_coffee_api.dto.order;

import easy.gc_coffee_api.entity.Orders;
import lombok.Getter;

@Getter
public class CreateOrderResponseDto {
    Long orderId;

    public CreateOrderResponseDto(Long orderId) {
        this.orderId = orderId;
    }

    public static CreateOrderResponseDto fromEntity(Orders savedOrder) {
        return new CreateOrderResponseDto(savedOrder.getId());
    }
}
