package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.dto.order.OrderDateRangeDto;
import easy.gc_coffee_api.dto.order.OrderResponseDto;
import easy.gc_coffee_api.usecase.order.model.OrderMenuModel;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.usecase.order.model.OrderDataRange;
import easy.gc_coffee_api.usecase.order.model.OrderMenuData;
import easy.gc_coffee_api.usecase.order.model.OrderMenuDatas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderListResponseDtoMapper {
    public List<OrderDateRangeDto> toDateRangeDto(Map<OrderDataRange, List<Orders>> dataRangeListMap, OrderMenuDatas orderMenuDatas) {
        List<OrderDateRangeDto> result = new ArrayList<>();
        for (OrderDataRange orderDataRange : dataRangeListMap.keySet()) {
            List<Orders> orders = dataRangeListMap.get(orderDataRange);
            List<OrderResponseDto> orderListResponseDtos = toOrderListResponseDtoList(orders, orderMenuDatas);
            result.add(new OrderDateRangeDto(orderDataRange.getFrom(), orderDataRange.getTo(), orderListResponseDtos));
        }
        return result;
    }

    public List<OrderResponseDto> toOrderListResponseDtoList(List<Orders> orders, OrderMenuDatas orderMenuDatas) {
        List<OrderResponseDto> result = new ArrayList<>();
        for (Orders order : orders) {
            List<OrderMenuData> orderMenuData = orderMenuDatas.get(order.getId());
            List<OrderMenuModel> orderMenuDtos = toOrderMenus(orderMenuData);

            OrderResponseDto orderListResponseDto = new OrderResponseDto(order.getId(), order.getEmail(), order.getAddress().getAddress(), order.getAddress().getZipCode(), order.getStatus(), order.getTotalPrice(), orderMenuDtos);
            result.add(orderListResponseDto);
        }

        return result;
    }

    private List<OrderMenuModel> toOrderMenus(List<OrderMenuData> orderMenuData) {
        List<OrderMenuModel> orderMenuDtos = new ArrayList<>();
        for (OrderMenuData orderMenu : orderMenuData) {
            OrderMenuModel orderMenuResponseDto = new OrderMenuModel(
                    orderMenu.getMenuId(),
                    orderMenu.getMenuName(),
                    orderMenu.getPrice(),
                    orderMenu.getQuantity(),
                    orderMenu.getThumbnailUrl()
            );
            orderMenuDtos.add(orderMenuResponseDto);
        }
        return orderMenuDtos;
    }
}
