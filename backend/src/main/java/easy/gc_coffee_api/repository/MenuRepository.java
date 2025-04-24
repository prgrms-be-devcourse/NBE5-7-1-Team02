package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {

    @Query("SELECT new easy.gc_coffee_api.dto.MenuResponseDto(m.name, m.price, m.category," +
            " CASE WHEN f IS NULL THEN NULL WHEN f IS NOT NULL THEN f.key END)" +
            " FROM Menu m LEFT JOIN File f ON m.thumnail.fileId = f.id" +
            " WHERE m.deletedAt IS NULL")
    List<MenuResponseDto> findAllNotDeleted();

    Optional<Menu> findByIdAndDeletedAtIsNull(Long menuId);
}
