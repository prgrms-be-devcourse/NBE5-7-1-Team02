package easy.gc_coffee_api.mail;

import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.usecase.order.model.OrderMenuData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderMailService {

    private final JavaMailSender mailSender;
    private final OrderMessageCreater orderMessageCreater;
    private final MessageHtmlReader messageHtmlReader;

    public void sendMail(String to, Orders orders, List<OrderMenuData> orderMenus) {
        try {
            String bodyMessage = messageHtmlReader.getMailBodyMessage(orders, orderMenus);
            MimeMessage message = orderMessageCreater.createMailMessage(to, bodyMessage);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
