package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.entity.OrderMenu;
import easy.gc_coffee_api.usecase.order.model.OrderMenuData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findByOrdersId(Long orderId);

    @Query("SELECT new easy.gc_coffee_api.usecase.order.model.OrderMenuData(om)  " +
            "From OrderMenu om " +
            "where om.orders.id in :orderIds ")
    List<OrderMenuData> findOrderMenusByOrderIds(List<Long> orderIds);


    @Query("""
            SELECT new easy.gc_coffee_api.usecase.order.model.OrderMenuData(om)
            FROM OrderMenu om
            WHERE om.orders.id = :orderId
            """)
    List<OrderMenuData> findAllByOrdersId(Long orderId);
}
