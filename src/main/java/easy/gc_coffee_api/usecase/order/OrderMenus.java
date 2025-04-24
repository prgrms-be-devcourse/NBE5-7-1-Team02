package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.entity.OrderMenu;

import java.util.ArrayList;
import java.util.List;

public class OrderMenus {
    private List<OrderMenu> orderMenus = new ArrayList<>();

    public void add(OrderMenu orderMenu){
        orderMenus.add(orderMenu);
    }


    public Integer calcTotalPrice() {
        return orderMenus.stream().mapToInt(OrderMenu::calculatePrice).sum();
    }
}
