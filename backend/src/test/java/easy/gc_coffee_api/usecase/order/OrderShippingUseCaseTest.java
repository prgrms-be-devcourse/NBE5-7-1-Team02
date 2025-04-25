package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.entity.Address;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderShippingUseCaseTest {

    @Mock
    private OrderRepository orderRepository;


    @Test
    void 주문배송처리(){
        Long orderId = 1L;
        Orders orders = new Orders(orderId, "test@email.com", new Address("adress", "4000"), 3000);
        when(orderRepository.findById(eq(orderId))).thenReturn(Optional.of(orders));

        OrderShippingUseCase orderShippingUseCase = new OrderShippingUseCase(orderRepository);
        orderShippingUseCase.execute(orderId);

        assertThat(orders.getShippingStatus()).isEqualTo(ShippingStatus.SHIPPING);
    }

    @Test
    void 없는_주문배송처리(){
        Long orderId = 1L;

        OrderShippingUseCase orderShippingUseCase = new OrderShippingUseCase(orderRepository);
        assertThatThrownBy(() -> orderShippingUseCase.execute(orderId)).isInstanceOf(EntityNotFoundException.class);
    }
}
