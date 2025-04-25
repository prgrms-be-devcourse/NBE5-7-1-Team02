package easy.gc_coffee_api.repository;

import easy.gc_coffee_api.usecase.menu.dto.MenuData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MenuRepositoryTests {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("JPQL 전체 조회 테스트")
    void jpqlTest() throws Exception {
        List<MenuData> items = menuRepository.findAllNotDeleted();
        for (MenuData item : items) {
            System.out.println(item.getId());
            System.out.println(items.size());
            System.out.println(item.getName());
        }
    }

    @Test
    @DisplayName("JPQL 싱글 조회 테스트(성공)")
    void jpqlSingleSelectSuccessTest() throws Exception {
        Long menuId = 103L; // 실제 DB에 존재하는 menu_id를 설정.
        Optional<MenuData> item = menuRepository.findOneNotDeleted(menuId);
        assertThat(item).isPresent();
        item.ifPresent(menu -> {
            System.out.println(menu.getId());
            System.out.println(menu.getThumbnailUrl());
        });
    }

    @Test
    @DisplayName("JPQL 싱글 조회 테스트(실패)")
    void jpqlSingleSelectFailTest() throws Exception {
        Long menuId = 100L; // 실제 DB에 존재하지 않는 menu_id를 설정.
        Optional<MenuData> item = menuRepository.findOneNotDeleted(menuId);
        assertThat(item).isNotPresent();
    }

}