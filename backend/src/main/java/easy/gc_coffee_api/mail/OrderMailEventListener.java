package easy.gc_coffee_api.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMailEventListener {

    private final OrderMailService orderMailService;

    @Async
    @EventListener(value = OrderMailMessage.class)
    public void handleOrderMailMessage(OrderMailMessage orderMailMessage) {
        orderMailService.sendMail(orderMailMessage.getTo(), orderMailMessage.getOrderResponseDto());
    }
}
