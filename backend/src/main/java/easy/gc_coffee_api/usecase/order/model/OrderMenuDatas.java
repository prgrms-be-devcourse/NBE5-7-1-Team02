package easy.gc_coffee_api.usecase.order.model;


import easy.gc_coffee_api.entity.OrderMenu;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMenuDatas {

    private final Map<Long, List<OrderMenuData>> datas;

    public OrderMenuDatas(List<OrderMenuData> datas) {
        this.datas = datas.stream().collect(Collectors.groupingBy(OrderMenuData::getOrderId));
    }

    public List<OrderMenuData> get(Long id) {
        return datas.getOrDefault(id, List.of());
    }


}
