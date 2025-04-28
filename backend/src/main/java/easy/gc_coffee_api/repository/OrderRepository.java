package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o From Orders o"+
            " JOIN FETCH o.orderMenus om"+
            " JOIN FETCH om.menu m")
    List<Orders> findAllWithMenus();
}
