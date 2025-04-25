package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {

    @Query("SELECT new easy.gc_coffee_api.dto.MenuResponseDto(m.id, m.name, m.price, m.category, f)" +
            " FROM Menu m LEFT JOIN File f ON m.thumbnail.fileId = f.id" +
            " WHERE m.deletedAt IS NULL")
    List<MenuResponseDto> findAllNotDeleted();

    @Query("SELECT new easy.gc_coffee_api.dto.MenuResponseDto(m.id, m.name, m.price, m.category, f)" +
            " FROM Menu m LEFT JOIN File f ON m.thumbnail.fileId = f.id" +
            " WHERE m.deletedAt IS NULL AND m.id = :menuId")
    Optional<MenuResponseDto> findOneNotDeleted(Long menuId);

    Optional<Menu> findByIdAndDeletedAtIsNull(Long menuId);
}
