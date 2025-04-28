package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.dto.order.OrderResponseDto;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.exception.order.OrderNotFoundException;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import easy.gc_coffee_api.usecase.order.model.OrderMenuData;
import easy.gc_coffee_api.usecase.order.model.OrderMenuModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;

    public OrderResponseDto execute(Long id, String email) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다.", 404));

        if (!order.getEmail().equals(email)) {
            throw new IllegalArgumentException("이메일이 일치하지 않습니다.");
        }

        List<OrderMenuData> orderMenuDatas = orderMenuRepository.findAllByOrdersId(id);

        List<OrderMenuModel> orderMenus = mapToOrderMenuModels(orderMenuDatas);

        return new OrderResponseDto(
                order.getId(),
                order.getEmail(),
                order.getAddress().getAddress(), // TODO Order 엔터티 Address flat 시켜도 될듯
                order.getAddress().getZipCode(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getTotalPrice(),
                orderMenus
        );
    }

    private List<OrderMenuModel> mapToOrderMenuModels(List<OrderMenuData> orderMenus) {
        return orderMenus.stream()
                .map(menu -> new OrderMenuModel(
                        menu.getMenuId(),
                        menu.getMenuName(),
                        menu.getPrice(),
                        menu.getQuantity()
                ))
                .toList();
    }
}
