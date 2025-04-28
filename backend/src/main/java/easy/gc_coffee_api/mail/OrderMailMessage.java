package easy.gc_coffee_api.mail;

import easy.gc_coffee_api.dto.order.OrderResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderMailMessage {

    private final String to;
    private final OrderResponseDto orderResponseDto;

}
