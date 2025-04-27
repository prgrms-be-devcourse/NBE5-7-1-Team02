package easy.gc_coffee_api.usecase.order;


import easy.gc_coffee_api.exception.order.OrderNotFoundException;
import easy.gc_coffee_api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipOrdersUseCase {

    private final JdbcTemplate jdbcTemplate;
    private final OrderRepository orderRepository;

    public void execute(List<Long> ids) {
        ids.forEach(id -> {
            orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("주문이 존재하지 않습니다. 주문 ID:" +id,404 ));
        });
        jdbcTemplate.batchUpdate(
                "UPDATE orders SET status = 'SHIPPED' WHERE id = ?", new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, ids.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return ids.size();
                    }
                }
        );
    }
}
