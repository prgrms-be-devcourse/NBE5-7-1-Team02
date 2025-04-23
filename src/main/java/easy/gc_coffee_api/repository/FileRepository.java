package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Long> {
}
