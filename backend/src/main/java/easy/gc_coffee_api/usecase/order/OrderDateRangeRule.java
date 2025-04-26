package easy.gc_coffee_api.usecase.order;

import easy.gc_coffee_api.entity.Orders;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDateRangeRule {

    public static final int BOUNDARY = 14;

    public Map<OrderDataRange, List<Orders>> groupByCreateDate(List<Orders> orders) {
        Map<OrderDataRange, List<Orders>> result = new HashMap<>();
        for (Orders order : orders) {
            LocalDateTime createdAt = order.getCreatedAt();
            OrderDataRange dateRangeFrom = createDateRangeFrom(createdAt);
            result.computeIfAbsent(dateRangeFrom, key -> new ArrayList<>()).add(order);
        }
        return result;
    }

    private OrderDataRange createDateRangeFrom(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        if (hour < BOUNDARY) {
            LocalDateTime from = dateTime
                    .minusDays(1)
                    .with(startTime());
            LocalDateTime to = dateTime
                    .with(endTime());
            return new OrderDataRange(from, to);
        }
        LocalDateTime from = dateTime
                .with(startTime());
        LocalDateTime to = from.plusDays(1).with(endTime());
        return new OrderDataRange(from, to);
    }

    private static LocalTime startTime() {
        return LocalTime.of(BOUNDARY, 0);
    }

    private static LocalTime endTime() {
        return LocalTime.of(BOUNDARY - 1, 59);
    }
}