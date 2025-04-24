package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long> {

    @Query("SELECT new easy.gc_coffee_api.dto.MenuResponseDto(m.name, m.price, m.category,case when f is null then null when f is not null then f.url end) FROM Menu m LEFT JOIN File f ON m.thumnail.fileId = f.id")
    List<MenuResponseDto> findAllByMenuResponseDto();
}
