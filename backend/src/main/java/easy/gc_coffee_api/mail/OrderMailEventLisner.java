package easy.gc_coffee_api.mail;

import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import easy.gc_coffee_api.usecase.order.model.OrderMenuData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMailEventLisner {

    private final OrderMailService orderMailService;
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Async
    @EventListener(value = OrderMailMessage.class)
    public void handleOrderMailMessage(OrderMailMessage orderMailMessage) {
        Optional<Orders> orders = orderRepository.findById(orderMailMessage.getOrderId());

        orders.ifPresent(order -> {
            List<OrderMenuData> orderMenus = orderMenuRepository.findAllByOrdersId(order.getId());
            orderMailService.sendMail(orderMailMessage.getTo(), order, orderMenus);
        });

    }
}
