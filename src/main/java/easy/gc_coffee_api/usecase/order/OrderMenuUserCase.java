package easy.gc_coffee_api.usecase.order;



import easy.gc_coffee_api.dto.OrderItemDto;
import easy.gc_coffee_api.dto.OrderRequestDto;
import easy.gc_coffee_api.entity.Address;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.OrderMenu;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
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

  public Long process(OrderRequestDto dto) {
    Orders order = Orders.builder()
        .email(dto.getEmail())
        .address(new Address(
            dto.getAddressdto().getAddress(),
            dto.getAddressdto().getZipCode()
        ))
        .totalPrice(0)
        .build();
    Orders savedOrder = orderRepository.save(order);
    int totalPrice = 0;

    for (OrderItemDto item : dto.getItems()) {
      Menu menu = menuRepository.findById(item.getMenuId())
          .orElseThrow(() -> new IllegalArgumentException(
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
      totalPrice += menu.getPrice() * item.getQuantity();
    }


    savedOrder.setTotalPrice(totalPrice);


    return savedOrder.getId();
  }
}
