package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.menu.MenuResponseDto;
import easy.gc_coffee_api.dto.menu.MenusResponseDto;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.usecase.menu.dto.MenuData;
import easy.gc_coffee_api.usecase.menu.dto.MenuDatas;
import easy.gc_coffee_api.usecase.menu.helper.MenuReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMenusUseCaseTests {
    @Mock
    private MenuReader menuReader;

    private GetMenusUseCase getMenusUseCase;

    @BeforeEach
    void setUp() {
        getMenusUseCase = new GetMenusUseCase(menuReader);
    }

    @Test
    @DisplayName("메뉴 리스트 조회")
    void getMenusTest() {
        // given
        MenuData firstDto =  new MenuData(1L,"Americano", 3000, Category.COFFEE_BEAN, 1L,"/storage/111.jpg","/storage/111.jpg");
        MenuData secondDto = new MenuData(2L,"CafeLatte", 4000, Category.COFFEE_BEAN, 2L,"/storage/111.jpg","/storage/111.jpg");
        List<MenuData> fakeMenus = Arrays.asList(
                firstDto,secondDto
        );

        when(menuReader.readAllNotDelete()).thenReturn(new MenuDatas(fakeMenus));

        // when
        MenusResponseDto result = getMenusUseCase.execute();

        // then
        MenuResponseDto first = result.getMenus().getFirst();
        assertThat(first.getId()).isEqualTo(firstDto.getId());
        assertThat(first.getName()).isEqualTo(firstDto.getName());
        assertThat(first.getPrice()).isEqualTo(firstDto.getPrice());
        assertThat(first.getCategory()).isEqualTo(firstDto.getCategory());
        assertThat(first.getFileId()).isEqualTo(firstDto.getFileId());
        assertThat(first.getThumbnailUrl()).isEqualTo(firstDto.getThumbnailUrl());

        MenuResponseDto second = result.getMenus().get(1);
        assertThat(second.getId()).isEqualTo(secondDto.getId());
        assertThat(second.getName()).isEqualTo(secondDto.getName());
        assertThat(second.getPrice()).isEqualTo(secondDto.getPrice());
        assertThat(second.getCategory()).isEqualTo(secondDto.getCategory());
        assertThat(second.getFileId()).isEqualTo(secondDto.getFileId());
        assertThat(second.getThumbnailUrl()).isEqualTo(secondDto.getThumbnailUrl());

    }
}