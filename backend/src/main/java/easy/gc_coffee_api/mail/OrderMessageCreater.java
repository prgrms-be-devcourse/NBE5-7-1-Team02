package easy.gc_coffee_api.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMessageCreater {

    private static final String FROM = "rhwlgns4386@gmail.com";

    private final JavaMailSender javaMailSender;

    public MimeMessage createMailMessage(String to, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setFrom(FROM);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("주문이 완료되었습니다.");
        mimeMessageHelper.setText(body, true);
        return message;
    }
}