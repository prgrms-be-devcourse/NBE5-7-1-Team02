package easy.gc_coffee_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import easy.gc_coffee_api.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShipOrderResponseDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime from;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime to;

    private List<Orders> orders;
}
