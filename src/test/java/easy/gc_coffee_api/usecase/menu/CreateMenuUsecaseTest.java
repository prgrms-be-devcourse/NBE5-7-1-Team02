package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateMenuUsecaseTest {

    @Mock
    private MenuRepository menuRepository;
    @Mock
    private ThumnailFatory thumnailFatory;

    private CreateMenuUsecase createMenuUsecase;

    @BeforeEach
    void setUp() {
        createMenuUsecase =  new CreateMenuUsecase(menuRepository, thumnailFatory);
    }

    @Test
    void 메뉴생성() {
        String menuName = "menuName";
        Category category = Category.COFFEE_BEAN;
        int price = 1000;
        Long entityId = 1L;
        Long fileId = 2L;
//        when(thumnailFatory.create(eq(fileId))).thenReturn(new Thumnail(1L));
        when(menuRepository.save(any())).thenReturn(new Menu(entityId, menuName, price, category, new Thumnail(null)));

        Long id = createMenuUsecase.process(menuName, category, price, fileId);

        assertThat(id).isEqualTo(entityId);
    }
}
