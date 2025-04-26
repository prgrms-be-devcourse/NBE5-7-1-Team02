package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.dto.OrderDateRangeDto;
import easy.gc_coffee_api.dto.OrderListResponseDto;
import easy.gc_coffee_api.dto.OrderMenuResponseDto;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.usecase.file.FileUrlTranslator;
import easy.gc_coffee_api.usecase.order.dto.OrderMenuData;
import easy.gc_coffee_api.usecase.order.dto.OrderMenuDatas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderListResponseDtoMapper {

    private final FileUrlTranslator fileUrlTranslator;

    public List<OrderDateRangeDto> toDateRangeDto(Map<OrderDataRange, List<Orders>> dataRangeListMap, OrderMenuDatas orderMenuDatas) {
        List<OrderDateRangeDto> result = new ArrayList<>();
        for (OrderDataRange orderDataRange : dataRangeListMap.keySet()) {
            List<Orders> orders = dataRangeListMap.get(orderDataRange);
            List<OrderListResponseDto> orderListResponseDtos = toOrderListResponseDtoList(orders, orderMenuDatas);
            result.add(new OrderDateRangeDto(orderDataRange.getFrom(), orderDataRange.getTo(), orderListResponseDtos));
        }
        return result;
    }

    public List<OrderListResponseDto> toOrderListResponseDtoList(List<Orders> orders, OrderMenuDatas orderMenuDatas) {
        List<OrderListResponseDto> result = new ArrayList<>();
        for (Orders order : orders) {
            List<OrderMenuData> orderMenuData = orderMenuDatas.get(order.getId());
            List<OrderMenuResponseDto> orderMenuDtos = toOrderMenus(orderMenuData);

            OrderListResponseDto orderListResponseDto = new OrderListResponseDto(order.getId(), order.getEmail(), order.getAddress().getAddress(), order.getAddress().getZipCode(), order.getStatus(), order.getTotalPrice(), orderMenuDtos);
            result.add(orderListResponseDto);
        }

        return result;
    }

    private List<OrderMenuResponseDto> toOrderMenus(List<OrderMenuData> orderMenuData) {
        List<OrderMenuResponseDto> orderMenuDtos = new ArrayList<>();
        for (OrderMenuData orderMenu : orderMenuData) {
            OrderMenuResponseDto orderMenuResponseDto = new OrderMenuResponseDto(orderMenu.getMenuName(),
                    orderMenu.getPrice(),
                    orderMenu.getQuantity(),
                    getFullPathUrl(orderMenu)
            );
            orderMenuDtos.add(orderMenuResponseDto);
        }
        return orderMenuDtos;
    }

    private String getFullPathUrl(OrderMenuData orderMenu) {
        if(orderMenu.hasThumbnailUrl()){
            return fileUrlTranslator.execute(orderMenu.getThumbnailUrl());
        }
        return null;
    }

}
