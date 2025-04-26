package easy.gc_coffee_api.usecase.order;


import easy.gc_coffee_api.dto.OrderListResponseDto;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import easy.gc_coffee_api.usecase.order.dto.OrderMenuData;
import easy.gc_coffee_api.usecase.order.dto.OrderMenuDatas;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GetOrderListUsecase {

    private final OrderRepository repository;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderListResponseDtoMapper orderListResponseDtoMapper;


    public List<OrderListResponseDto> execute() {
        List<Orders> orders = repository.findAll();
        List<OrderMenuData> orderMenus = orderMenuRepository.findOrderMenusByOrderIds(toKeys(orders));
        OrderMenuDatas orderMenuDatas = new OrderMenuDatas(orderMenus);
        return orderListResponseDtoMapper.toOrderListResponseDtoList(orders,orderMenuDatas);
    }

    private static List<Long> toKeys(List<Orders> all) {
        return all.stream().map(Orders::getId).collect(Collectors.toList());
    }

}
