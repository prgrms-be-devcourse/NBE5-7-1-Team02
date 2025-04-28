package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.usecase.file.FileRemover;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMenuUseCaseTests {

    @Mock
    private MenuRepository menuRepository;

    private DeleteMenuUseCase deleteMenuUseCase;

    @Mock
    private FileRemover fileRemover;

    @BeforeEach
    void setUp() {
        deleteMenuUseCase = new DeleteMenuUseCase(menuRepository, fileRemover);
    }

    @Test
    @DisplayName("메뉴 삭제")
    void deleteMenuTest() throws Exception {
        // given
        Long menuId = 2L;
        Menu findMenu = new Menu(menuId, "Americano", 3000, Category.COFFEE_BEAN, new Thumbnail(1L));
        when(menuRepository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.of(findMenu));
        // when
        // 2번 메뉴를 찾아옵니다.
        // 찾아온 메뉴를 삭제합니다.
        // 찾아온 메뉴가 가지고 있던 파일을 삭제합니다.
        deleteMenuUseCase.execute(menuId);
        // then
        verify(fileRemover, times(1)).remove(eq(1L));
        assertThat(findMenu.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("메뉴 삭제 (메뉴가 없는 경우)")
    void deleteMenuWhenMenuNotExistTest() throws Exception {
        // given
        Long menuId = 1L;
        when(menuRepository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.empty());
        // when / then
        assertThatThrownBy(() -> deleteMenuUseCase.execute(menuId)).isInstanceOf(MenuNotFoundException.class);
    }

    @Test
    @DisplayName("메뉴 삭제 (파일이 없는 경우)")
    void deleteMenuWhenFileNotExistTest() throws Exception {
        // given
        Long menuId = 2L;
        Menu findMenu = new Menu(menuId, "Americano", 3000, Category.COFFEE_BEAN, new Thumbnail(null));
        when(menuRepository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.of(findMenu));
        // then
        deleteMenuUseCase.execute(menuId);

        verify(fileRemover, never()).remove(any());
    }
}