package easy.gc_coffee_api.dto;

import easy.gc_coffee_api.entity.Orders;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    Long orderId;

    public static OrderResponseDto fromEntity(Orders savedOrder) {
        return OrderResponseDto.builder().orderId(savedOrder.getId()).build();
    }
}
