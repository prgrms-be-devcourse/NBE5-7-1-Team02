package easy.gc_coffee_api.mail;

import easy.gc_coffee_api.dto.order.OrderResponseDto;
import easy.gc_coffee_api.entity.common.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MessageHtmlReader {

    private final TemplateEngine templateEngine;

    public String getMailBodyMessage(OrderResponseDto orderResponseDto) {
        Context context = createModel(orderResponseDto);
        return templateEngine.process("mailbody", context);
    }

    private Context createModel(OrderResponseDto orderResponseDto) {
        Context context = new Context();
        context.setVariable("orderId", orderResponseDto.getId());
        context.setVariable("email", orderResponseDto.getEmail());
        context.setVariable("creatAt", orderResponseDto.getCreatedAt());
        context.setVariable("address", orderResponseDto.getAddress());
        context.setVariable("zipcode", orderResponseDto.getZipCode());
        context.setVariable("status", orderResponseDto.getStatus() == OrderStatus.PENDING ? "배송대기중" : "발송완료");
        context.setVariable("total_price", orderResponseDto.getTotalPrice());
        context.setVariable("ordermenus", orderResponseDto.getMenus());
        return context;
    }
}