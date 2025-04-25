package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetMenuUseCaseTests {

    @Mock
    private MenuRepository menuRepository;

    private GetMenuUseCase getMenuUseCase;

    @BeforeEach
    void setUp() {
        getMenuUseCase = new GetMenuUseCase(menuRepository);
    }
    
    @Test
    @DisplayName("메뉴 아이디로 삭제되지 않은 메뉴 조회")
    void getMenuSuccessTest() throws Exception {
        //given
        Long menuId = 101L;
        String prefix = "http://localhost:8084/";
        File file = new File(1L, "image/jpeg", "storage/test.jpg");
        MenuResponseDto findMenu = new MenuResponseDto(menuId, "Espresso", 2500, Category.COFFEE_BEAN, file);
        //when
        when(menuRepository.findOneNotDeleted(menuId)).thenReturn(Optional.of(findMenu));
        //then
        MenuResponseDto result = getMenuUseCase.execute(menuId);

        assertThat(result.getId()).isEqualTo(findMenu.getId());
        assertThat(result.getName()).isEqualTo(findMenu.getName());
        assertThat(result.getFileId()).isEqualTo(findMenu.getFileId());
        assertThat(result.getThumbnailUrl()).startsWith(prefix);
        assertThat(result.getThumbnailUrl()).isEqualTo(findMenu.getThumbnailUrl());
        assertThat(result.getFileId()).isEqualTo(findMenu.getFileId());
    }

    @Test
    @DisplayName("메뉴 아이디로 삭제된 메뉴 조회")
    void getMenuFailTest() throws Exception {
        // given
        Long menuId = 99L;
        when(menuRepository.findOneNotDeleted(eq(menuId))).thenReturn(Optional.empty());
        // when / then
        assertThatThrownBy(() -> getMenuUseCase.execute(menuId)).isInstanceOf(MenuNotFoundException.class);

    }
}