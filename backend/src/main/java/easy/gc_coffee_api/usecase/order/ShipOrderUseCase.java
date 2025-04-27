package easy.gc_coffee_api.usecase.order;


import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipOrderUseCase {

    private final OrderRepository orderRepository;

    @Transactional
    public void execute(Long orderId){
        Orders order = orderRepository.findById(orderId).orElseThrow(()->new MenuNotFoundException("주문 존재x",400));

        order.ship();
    }
}
