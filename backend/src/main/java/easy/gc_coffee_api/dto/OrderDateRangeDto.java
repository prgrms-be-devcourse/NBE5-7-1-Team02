package easy.gc_coffee_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDateRangeDto {
    private LocalDateTime from;
    private LocalDateTime to;
    private List<OrderListResponseDto> orders;
}
