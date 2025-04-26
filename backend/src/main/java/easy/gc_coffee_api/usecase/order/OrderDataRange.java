package easy.gc_coffee_api.usecase.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDataRange {
    private LocalDateTime from;
    private LocalDateTime to;
}
