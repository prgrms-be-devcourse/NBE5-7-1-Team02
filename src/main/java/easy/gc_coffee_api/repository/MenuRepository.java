package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Long> {
}
