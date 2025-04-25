package easy.gc_coffee_api.usecase.order;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShipOrdersUseCase {

    private final JdbcTemplate jdbcTemplate;

    public void execute(List<Long> ids) {
        jdbcTemplate.batchUpdate("update orders set shipping_status = 'SHIPPING' where id = ?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, ids.get(i));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });
    }
}
