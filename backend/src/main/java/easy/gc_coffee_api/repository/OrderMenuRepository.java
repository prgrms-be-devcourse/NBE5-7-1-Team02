package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.entity.OrderMenu;
import java.util.List;

import easy.gc_coffee_api.usecase.order.dto.OrderMenuData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderMenuRepository extends JpaRepository<OrderMenu,Long> {
  List<OrderMenu> findByOrdersId(Long orderId);

  @Query("SELECT new easy.gc_coffee_api.usecase.order.dto.OrderMenuData(om,f)  " +
          "From OrderMenu om left join File f on om.menu.thumbnail.fileId = f.id " +
          "where om.orders.id in :menuIds ")
  List<OrderMenuData> findOrderMenusByOrderIds(List<Long>  menuIds);
}
