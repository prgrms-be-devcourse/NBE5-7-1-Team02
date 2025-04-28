package easy.gc_coffee_api.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderMailMessage {

    private final String to;
    private final Long orderId;

}
