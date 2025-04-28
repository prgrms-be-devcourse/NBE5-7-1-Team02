package easy.gc_coffee_api.mail;

import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.entity.common.OrderStatus;
import easy.gc_coffee_api.usecase.order.model.OrderMenuData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageHtmlReader {

    private final TemplateEngine templateEngine;

    public String getMailBodyMessage(Orders orders, List<OrderMenuData> orderMenus) {
        Context context = createModel(orders, orderMenus);
        return templateEngine.process("mailbody", context);
    }

    private static Context createModel(Orders orders, List<OrderMenuData> orderMenus) {
        Context context = new Context();
        context.setVariable("orderId", orders.getId());
        context.setVariable("email", orders.getEmail());
        context.setVariable("creatAt", orders.getCreatedAt());
        context.setVariable("address", orders.getAddress().getAddress());
        context.setVariable("zipcode", orders.getAddress().getZipCode());
        context.setVariable("status", orders.getStatus() == OrderStatus.PENDING ? "배송대기중" : "발송완료");
        context.setVariable("total_price", orders.getTotalPrice());
        context.setVariable("ordermenus", orderMenus);
        return context;
    }
}