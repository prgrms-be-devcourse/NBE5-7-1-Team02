package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.entity.OrderMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu,Long> {
  List<OrderMenu> findByOrdersId(Long orderId);
}
