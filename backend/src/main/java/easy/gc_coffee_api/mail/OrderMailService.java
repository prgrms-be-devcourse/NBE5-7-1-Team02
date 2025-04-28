package easy.gc_coffee_api.mail;

import easy.gc_coffee_api.dto.order.OrderResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderMailService {

    private final JavaMailSender mailSender;
    private final OrderMessageCreater orderMessageCreater;
    private final MessageHtmlReader messageHtmlReader;

    public void sendMail(String to,OrderResponseDto orderResponseDto) {
        try {
            String bodyMessage = messageHtmlReader.getMailBodyMessage(orderResponseDto);
            MimeMessage message = orderMessageCreater.createMailMessage(to, bodyMessage);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
