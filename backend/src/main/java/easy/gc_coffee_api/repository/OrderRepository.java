package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
