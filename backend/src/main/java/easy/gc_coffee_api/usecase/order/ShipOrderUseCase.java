package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShipOrderUseCase {

    private final OrderRepository orderRepository;

    @Transactional
    public void execute(Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("주문을 찾을 수 없습니다"));
        orders.ship();
    }
}
