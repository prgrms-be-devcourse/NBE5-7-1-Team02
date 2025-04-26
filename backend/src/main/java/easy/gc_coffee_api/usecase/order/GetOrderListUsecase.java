package easy.gc_coffee_api.usecase.order;


import easy.gc_coffee_api.dto.order.OrderDateRangeDto;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import easy.gc_coffee_api.usecase.order.model.OrderDataRange;
import easy.gc_coffee_api.usecase.order.model.OrderMenuData;
import easy.gc_coffee_api.usecase.order.model.OrderMenuDatas;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GetOrderListUsecase {

    private final OrderRepository repository;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderListResponseDtoMapper orderListResponseDtoMapper;
    private final OrderDateRangeRule orderDateRangeRule;


    public List<OrderDateRangeDto> execute() {

        List<Orders> orders = getOrders();
        OrderMenuDatas orderMenuDatas = getOrderMenuDatas(orders);
        Map<OrderDataRange, List<Orders>> orderDateRangeGroup = orderDateRangeRule.groupByCreateDate(orders);

        List<OrderDateRangeDto> result = orderListResponseDtoMapper.toDateRangeDto(orderDateRangeGroup, orderMenuDatas);
        sortDescByFromDate(result);
        return result;
    }

    private static void sortDescByFromDate(List<OrderDateRangeDto> orderDateRangeDtos) {
        Collections.sort(orderDateRangeDtos, (o1, o2) -> o2.getFrom().compareTo(o1.getFrom()));
    }

    private List<Orders> getOrders() {
        return repository.findAll();
    }

    private OrderMenuDatas getOrderMenuDatas(List<Orders> orders) {
        List<OrderMenuData> orderMenus = orderMenuRepository.findOrderMenusByOrderIds(toKeys(orders));
        return new OrderMenuDatas(orderMenus);
    }

    private static List<Long> toKeys(List<Orders> all) {
        return all.stream().map(Orders::getId).collect(Collectors.toList());
    }


}
