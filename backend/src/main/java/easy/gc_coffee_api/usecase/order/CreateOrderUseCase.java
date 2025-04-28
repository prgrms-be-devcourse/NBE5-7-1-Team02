package easy.gc_coffee_api.usecase.order;


import easy.gc_coffee_api.dto.order.CreateOrderRequestDto;
import easy.gc_coffee_api.dto.order.OrderItemDto;
import easy.gc_coffee_api.dto.order.OrderResponseDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.OrderMenu;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.mail.OrderMailMessage;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import easy.gc_coffee_api.usecase.order.model.OrderMenuModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final JdbcTemplate jdbcTemplate;


    public OrderResponseDto execute(CreateOrderRequestDto dto) {
        List<OrderItemDto> orderItemDtos = dto.getItems(); // quantity

        List<Long> menuIds = orderItemDtos.stream().map(OrderItemDto::getMenuId).toList();

        Orders savedOrder = saveOrders(dto);

        List<Menu> menus = menuRepository.findMenuByMenuIds(menuIds);

        Map<Long,List<Menu>> menuGroup = menus.stream().collect(Collectors.groupingBy(Menu::getId));

        List<OrderMenu> orderMenus = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtos) {
            Menu menu = menuGroup.get(orderItemDto.getMenuId()).getFirst();
            OrderMenu orderMenu = OrderMenu.builder()
                    .orders(savedOrder)
                    .price(menu.getPrice())
                    .menu(menu)
                    .name(menu.getName())
                    .quantity(orderItemDto.getQuantity())
                    .build();


            orderMenus.add(orderMenu);
        }


        // OrderMenuDatas 엔터티 리스트가 필요
        saveOrderMenus(orderMenus);

        savedOrder.setTotalPrice(calcTotalPrice(orderMenus));

        List<OrderMenuModel> orderMenuModels = mapToOrderMenuModels(orderMenus);

        eventPublisher.publishEvent(new OrderMailMessage(savedOrder.getEmail(), savedOrder.getId()));
        return new OrderResponseDto(
                savedOrder.getId(),
                savedOrder.getEmail(),
                savedOrder.getAddress().getAddress(),
                savedOrder.getAddress().getZipCode(),
                savedOrder.getStatus(),
                savedOrder.getCreatedAt(),
                savedOrder.getTotalPrice(),
                orderMenuModels
        );
    }

    private Orders saveOrders(CreateOrderRequestDto dto) {
        Orders order = Orders.builder()
                .email(dto.getEmail())
                .address(dto.getAddress())
                .totalPrice(0)
                .build();
        return orderRepository.save(order);
    }

    private List<OrderMenuModel> mapToOrderMenuModels(List<OrderMenu> orderMenus) {
        return orderMenus.stream()
                .map(menu -> new OrderMenuModel(
                        menu.getMenu().getId(),
                        menu.getName(),
                        menu.getPrice(),
                        menu.getQuantity()
                ))
                .toList();
    }

    private void saveOrderMenus(List<OrderMenu> orderMenus) {
        String sql = """
                INSERT INTO order_menu (order_id, menu_id, name, price, quantity, created_at, updated_at)
                VALUES (?,?,?,?,?,?,?)
                """;

        LocalDateTime localDateTime = LocalDateTime.now();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OrderMenu orderMenu = orderMenus.get(i);

                ps.setLong(1, orderMenu.getOrders().getId());
                ps.setLong(2, orderMenu.getMenu().getId());
                ps.setString(3, orderMenu.getMenu().getName());
                ps.setLong(4, orderMenu.getPrice());
                ps.setLong(5, orderMenu.getQuantity());
                ps.setTimestamp(6, Timestamp.valueOf(localDateTime));
                ps.setTimestamp(7, Timestamp.valueOf(localDateTime));
            }

            @Override
            public int getBatchSize() {
                return orderMenus.size();
            }
        });
    }

    private int calcTotalPrice(List<OrderMenu> orderMenus){
        return orderMenus.stream().mapToInt(OrderMenu::calculatePrice).sum();
    }
}
