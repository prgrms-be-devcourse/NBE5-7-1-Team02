package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetOrderListUseCaseTests {

    private GetOrderListUseCase getOrderListUsecase;
    @Mock
    private OrderRepository repository;
    @Mock
    private OrderMenuRepository orderMenuRepository;
    @Mock
    private OrderDateRangeRule rule;

    private final String PREFIX = "/usecase/order";
    OrderListResponseDtoMapper orderListResponseDtoMapper = new OrderListResponseDtoMapper();

    @BeforeEach
    void setUp() {
        getOrderListUsecase = new GetOrderListUseCase(repository, orderMenuRepository, orderListResponseDtoMapper, rule);
    }

//    @Test
//    void 주문조회() {
//        long orderId = 1L;
//        String email = "test1@email.com";
//        int totalPrice = 3000;
//        Address adress = new Address("adress", "4000");
//        when(repository.findAll()).thenReturn(List.of(new Orders(orderId, email, adress, totalPrice)));
//
//        String menuName = "메뉴";
//        when(orderMenuRepository.findOrderMenusByOrderIds(eq(List.of(orderId)))).thenReturn(List.of(new OrderMenuData(orderId, menuName, totalPrice, 1, "/path")));
//
//        List<GetOrderListUsecase.DateRangeDto> execute = getOrderListUsecase.execute();
//
//        OrderListResponseDto orderListResponseDto = execute.getFirst().getOrders().getFirst();
//        assertThat(orderListResponseDto.getOrderId()).isEqualTo(orderId);
//        assertThat(orderListResponseDto.getEmail()).isEqualTo(email);
//        assertThat(orderListResponseDto.getTotalPrice()).isEqualTo(totalPrice);
//        assertThat(orderListResponseDto.getAddress()).isEqualTo(adress);
//
//        OrderMenuResponseDto orderMenuResponseDto = orderListResponseDto.getOrderMenus().getFirst();
//        assertThat(orderMenuResponseDto.getMenuName()).isEqualTo(menuName);
//        assertThat(orderMenuResponseDto.getPrice()).isEqualTo(totalPrice);
//        assertThat(orderMenuResponseDto.getQuantity()).isEqualTo(1);
//        assertThat(orderMenuResponseDto.getThumbnailUrl()).isEqualTo(PREFIX + "/path");
//    }

//    @Test
//    void 주문조회_메뉴_없음() {
//        long orderId = 1L;
//        String email = "test1@email.com";
//        int totalPrice = 3000;
//        Address adress = new Address("adress", "4000");
//        when(repository.findAll()).thenReturn(List.of(new Orders(orderId, email, adress, totalPrice)));
//
//        when(orderMenuRepository.findOrderMenusByOrderIds(eq(List.of(orderId)))).thenReturn(List.of());
//
//        List<GetOrderListUsecase.DateRangeDto> execute = getOrderListUsecase.execute();
//
//        OrderListResponseDto orderListResponseDto = execute.getFirst().getOrders().getFirst();
//        assertThat(orderListResponseDto.getOrderId()).isEqualTo(orderId);
//        assertThat(orderListResponseDto.getEmail()).isEqualTo(email);
//        assertThat(orderListResponseDto.getTotalPrice()).isEqualTo(totalPrice);
//        assertThat(orderListResponseDto.getAddress()).isEqualTo(adress);
//
//        assertThat(orderListResponseDto.getOrderMenus()).isEmpty();
//    }
}