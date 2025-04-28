package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.usecase.menu.dto.MenuData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT new easy.gc_coffee_api.usecase.menu.dto.MenuData(m, f)" +
            " FROM Menu m LEFT JOIN File f ON m.thumbnail.fileId = f.id" +
            " WHERE m.deletedAt IS NULL")
    List<MenuData> findAllNotDeleted();

    @Query("SELECT new easy.gc_coffee_api.usecase.menu.dto.MenuData(m, f)" +
            " FROM Menu m LEFT JOIN File f ON m.thumbnail.fileId = f.id" +
            " WHERE m.deletedAt IS NULL AND m.id = :menuId")
    Optional<MenuData> findOneNotDeleted(Long menuId);

    Optional<Menu> findByIdAndDeletedAtIsNull(Long menuId);
}
