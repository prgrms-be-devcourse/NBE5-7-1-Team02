package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.dto.MenuResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MenuRepositoryTests {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("JPQL 테스트")
    void jpql_test() throws Exception {

        List<MenuResponseDto> items = menuRepository.findAllNotDeleted();
        for (MenuResponseDto item : items) {
            System.out.println(items.size());
            System.out.println(item.getName());
        }
    
    }
}