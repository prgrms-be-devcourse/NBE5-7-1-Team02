package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.dto.OrderListResponseDto;
import easy.gc_coffee_api.dto.OrderMenuResponseDto;
import easy.gc_coffee_api.entity.Address;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import easy.gc_coffee_api.usecase.file.FileUrlTranslator;
import easy.gc_coffee_api.usecase.order.dto.OrderMenuData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrderListUsecaseTest {

    private GetOrderListUsecase getOrderListUsecase;
    @Mock
    private OrderRepository repository;
    @Mock
    private OrderMenuRepository orderMenuRepository;

    private final String PREFIX = "/usecase/order";
    OrderListResponseDtoMapper orderListResponseDtoMapper = new OrderListResponseDtoMapper(new FileUrlTranslator(PREFIX));

    @BeforeEach
    void setUp() {
        getOrderListUsecase = new GetOrderListUsecase(repository, orderMenuRepository, orderListResponseDtoMapper);
    }

    @Test
    void 주문조회() {
        long orderId = 1L;
        String email = "test1@email.com";
        int totalPrice = 3000;
        Address adress = new Address("adress", "4000");
        when(repository.findAll()).thenReturn(List.of(new Orders(orderId, email, adress, totalPrice)));

        String menuName = "메뉴";
        when(orderMenuRepository.findOrderMenusByOrderIds(eq(List.of(orderId)))).thenReturn(List.of(new OrderMenuData(orderId, menuName, totalPrice, 1, "/path")));

        List<OrderListResponseDto> execute = getOrderListUsecase.execute();

        OrderListResponseDto orderListResponseDto = execute.getFirst();
        assertThat(orderListResponseDto.getOrderId()).isEqualTo(orderId);
        assertThat(orderListResponseDto.getEmail()).isEqualTo(email);
        assertThat(orderListResponseDto.getTotalPrice()).isEqualTo(totalPrice);
        assertThat(orderListResponseDto.getAddress()).isEqualTo(adress);

        OrderMenuResponseDto orderMenuResponseDto = orderListResponseDto.getOrderMenus().getFirst();
        assertThat(orderMenuResponseDto.getMenuName()).isEqualTo(menuName);
        assertThat(orderMenuResponseDto.getPrice()).isEqualTo(totalPrice);
        assertThat(orderMenuResponseDto.getQuantity()).isEqualTo(1);
        assertThat(orderMenuResponseDto.getThumbnailUrl()).isEqualTo(PREFIX + "/path");
    }

    @Test
    void 주문조회_메뉴_없음() {
        long orderId = 1L;
        String email = "test1@email.com";
        int totalPrice = 3000;
        Address adress = new Address("adress", "4000");
        when(repository.findAll()).thenReturn(List.of(new Orders(orderId, email, adress, totalPrice)));

        when(orderMenuRepository.findOrderMenusByOrderIds(eq(List.of(orderId)))).thenReturn(List.of());

        List<OrderListResponseDto> execute = getOrderListUsecase.execute();

        OrderListResponseDto orderListResponseDto = execute.getFirst();
        assertThat(orderListResponseDto.getOrderId()).isEqualTo(orderId);
        assertThat(orderListResponseDto.getEmail()).isEqualTo(email);
        assertThat(orderListResponseDto.getTotalPrice()).isEqualTo(totalPrice);
        assertThat(orderListResponseDto.getAddress()).isEqualTo(adress);

        assertThat(orderListResponseDto.getOrderMenus()).isEmpty();
    }
}