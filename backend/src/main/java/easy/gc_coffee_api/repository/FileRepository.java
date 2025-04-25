package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File,Long> {

    Optional<File> findByIdAndDeletedAtIsNull(Long fileId);
}
