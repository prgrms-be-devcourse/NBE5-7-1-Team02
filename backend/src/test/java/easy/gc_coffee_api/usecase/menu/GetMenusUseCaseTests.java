package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.dto.MenusResponseDto;
import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.MenuRepository;
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
    private MenuRepository menuRepository;

    private GetMenusUseCase getMenusUseCase;

    @BeforeEach
    void setUp() {
        getMenusUseCase = new GetMenusUseCase(menuRepository);
    }

    @Test
    @DisplayName("메뉴 리스트 조회")
    void getMenusTest() {
        File file1 = new File(1L, "image/jpeg", "/storage/111.jpg");
        File file2 = new File(2L, "image/jpeg", "/storage/111.jpg");
        // given
        MenuResponseDto firstDto =  new MenuResponseDto(1L,"Americano", 3000, Category.COFFEE_BEAN, file1);
        MenuResponseDto secondDto = new MenuResponseDto(2L,"CafeLatte", 4000, Category.COFFEE_BEAN, file2);
        List<MenuResponseDto> fakeMenus = Arrays.asList(
                firstDto,secondDto
        );

        when(menuRepository.findAllNotDeleted()).thenReturn(fakeMenus);

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