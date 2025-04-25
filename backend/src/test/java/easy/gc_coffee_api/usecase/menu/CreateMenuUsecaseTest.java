package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.entity.common.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateMenuUsecaseTest {

    @Mock
    private MenuSaver menuSaver;
    @Mock
    private ThumbnailFactory thumbnailFactory;

    private CreateMenuUsecase createMenuUsecase;

    @BeforeEach
    void setUp() {
        createMenuUsecase =  new CreateMenuUsecase(menuSaver, thumbnailFactory);
    }

    @Test
    void 메뉴생성() {
        String menuName = "menuName";
        Category category = Category.COFFEE_BEAN;
        int price = 1000;
        Long entityId = 1L;
        Long fileId = 2L;

        Thumbnail thumbnail = new Thumbnail(2L, "image/jpeg");
        when(thumbnailFactory.create(eq(fileId))).thenReturn(thumbnail);
        when(menuSaver.save(eq(menuName),eq(category),eq(price),eq(thumbnail))).thenReturn(new Menu(entityId, menuName, price, category, thumbnail));

        Long id = createMenuUsecase.execute(menuName, category, price, fileId);

        assertThat(id).isEqualTo(entityId);
    }
}
