package easy.gc_coffee_api.usecase.order;



import easy.gc_coffee_api.dto.OrderItemDto;
import easy.gc_coffee_api.dto.OrderRequestDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.OrderMenu;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderMenuUserCase {

  private final OrderRepository orderRepository;
  private final MenuRepository menuRepository;
  private final OrderMenuRepository orderMenuRepository;

  public Long execute(OrderRequestDto dto) {
    Orders savedOrder = saveOrders(dto);

    OrderMenus orderMenus = saveOrderMenus(dto, savedOrder);
    savedOrder.setTotalPrice(orderMenus.calcTotalPrice());

    return savedOrder.getId();
  }

  private Orders saveOrders(OrderRequestDto dto) {
    Orders order = Orders.builder()
        .email(dto.getEmail())
        .address(dto.getAddress())
        .totalPrice(0)
        .build();
    return orderRepository.save(order);
  }

  private OrderMenus saveOrderMenus(OrderRequestDto dto, Orders savedOrder) {

    OrderMenus orderMenus = new OrderMenus();

    for (OrderItemDto item : dto.getItems()) {
      OrderMenu orderMenu = saveMenu(savedOrder, item);
      orderMenus.add(orderMenu);
    }

    return orderMenus;
  }

  private OrderMenu saveMenu(Orders savedOrder, OrderItemDto item) {
    Menu menu = menuRepository.findById(item.getMenuId())
        .orElseThrow(() -> new EntityNotFoundException(
            "존재하지 않는 메뉴 ID: " + item.getMenuId()
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
