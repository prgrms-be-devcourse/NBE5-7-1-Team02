package easy.gc_coffee_api.usecase.order;



import easy.gc_coffee_api.dto.order.OrderItemDto;
import easy.gc_coffee_api.dto.order.CreateOrderRequestDto;
import easy.gc_coffee_api.dto.order.CreateOrderResponseDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.OrderMenu;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import easy.gc_coffee_api.usecase.order.model.OrderMenus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderMenuUseCase {

  private final OrderRepository orderRepository;
  private final MenuRepository menuRepository;
  private final OrderMenuRepository orderMenuRepository;

  public CreateOrderResponseDto execute(CreateOrderRequestDto dto) {
    Orders savedOrder = saveOrders(dto);

    OrderMenus orderMenus = saveOrderMenus(dto, savedOrder);
    savedOrder.setTotalPrice(orderMenus.calcTotalPrice());

    return CreateOrderResponseDto.fromEntity(savedOrder);
  }

  private Orders saveOrders(CreateOrderRequestDto dto) {
    Orders order = Orders.builder()
        .email(dto.getEmail())
        .address(dto.getAddress())
        .totalPrice(0)
        .build();
    return orderRepository.save(order);
  }

  private OrderMenus saveOrderMenus(CreateOrderRequestDto dto, Orders savedOrder) {

    OrderMenus orderMenus = new OrderMenus();

    for (OrderItemDto item : dto.getItems()) {
      OrderMenu orderMenu = saveMenu(savedOrder, item);
      orderMenus.add(orderMenu);
    }

    return orderMenus;
  }

  private OrderMenu saveMenu(Orders savedOrder, OrderItemDto item) {
    Menu menu = menuRepository.findByIdAndDeletedAtIsNull(item.getMenuId())
        .orElseThrow(() -> new MenuNotFoundException(
            "존재하지 않는 메뉴 ID: " + item.getMenuId(), 404
        ));

    OrderMenu orderMenu = OrderMenu.builder()
        .name(menu.getName())
        .price(menu.getPrice())
        .quantity(item.getQuantity())
        .menu(menu)
        .orders(savedOrder)
        .build();

    orderMenuRepository.save(orderMenu);
    return orderMenu;
  }
}
