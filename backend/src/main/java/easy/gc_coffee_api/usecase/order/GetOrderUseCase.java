package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.dto.order.OrderResponseDto;
import easy.gc_coffee_api.dto.order.common.OrderMenuModel;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.exception.order.OrderNotFoundException;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import easy.gc_coffee_api.usecase.order.model.OrderMenuData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;

    public OrderResponseDto execute(Long id) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다.", 404));

        List<OrderMenuData> orderMenuDatas = orderMenuRepository.findAllByOrdersId(id);

        List<OrderMenuModel> orderMenus = mapToOrderMenuModels(orderMenuDatas);

        return new OrderResponseDto(
                order.getId(),
                order.getEmail(),
                order.getAddress().getAddress(), // TODO Order 엔터티 Address flat 시켜도 될듯
                order.getAddress().getZipCode(),
                order.getStatus(),
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
                        menu.getQuantity(),
                        menu.getThumbnailUrl()
                ))
                .toList();
    }
}
