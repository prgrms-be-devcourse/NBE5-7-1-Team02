package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.usecase.menu.dto.MenuData;
import easy.gc_coffee_api.usecase.menu.helper.MenuReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMenuUseCaseTests {

    @Mock
    private MenuReader menuReader;

    private GetMenuUseCase getMenuUseCase;

    @BeforeEach
    void setUp() {
        getMenuUseCase = new GetMenuUseCase(menuReader);
    }

    @Test
    @DisplayName("메뉴 아이디로 삭제되지 않은 메뉴 조회")
    void getMenuSuccessTest() throws Exception {
        //given
        Long menuId = 101L;
        String prefix = "http://localhost:8084/";
        File file = new File(1L, "image/jpeg", "storage/test.jpg");
        MenuData findMenu = new MenuData(menuId, "Espresso", 2500, Category.COFFEE_BEAN, file.getId(), prefix + file.getKey(), prefix + file.getKey());
        //when
        when(menuReader.findOneNotDeleted(eq(menuId))).thenReturn(findMenu);
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
        when(menuReader.findOneNotDeleted(eq(menuId))).thenThrow(new MenuNotFoundException("이미 삭제된 메뉴이거나 존재하지 않는 메뉴입니다.", 400));
        // when / then
        assertThatThrownBy(() -> getMenuUseCase.execute(menuId)).isInstanceOf(MenuNotFoundException.class);

    }
}