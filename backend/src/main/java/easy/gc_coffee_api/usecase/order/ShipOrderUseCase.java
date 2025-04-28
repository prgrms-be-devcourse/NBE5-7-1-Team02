package easy.gc_coffee_api.usecase.order;


import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.entity.common.OrderStatus;
import easy.gc_coffee_api.exception.order.OrderAlreadyShippedException;
import easy.gc_coffee_api.exception.order.OrderNotFoundException;
import easy.gc_coffee_api.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipOrderUseCase {

    private final OrderRepository orderRepository;

    @Transactional
    public void execute(Long orderId) throws OrderNotFoundException, OrderAlreadyShippedException {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("주문이 존재하지 않습니다.", 404));

        if (order.getStatus() == OrderStatus.SHIPPED) {
            throw new OrderAlreadyShippedException("이미 발송된 상품입니다.", 409);
        }
        order.ship();
    }
}
