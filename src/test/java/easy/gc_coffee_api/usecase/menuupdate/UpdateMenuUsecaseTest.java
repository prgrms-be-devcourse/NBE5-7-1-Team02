package easy.gc_coffee_api.usecase.menuupdate;

import easy.gc_coffee_api.dto.UpdateMenuRequestDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.usecase.menu.ThumnailFatory;
import easy.gc_coffee_api.usecase.menu.UpdateMenuUsecase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

public class UpdateMenuUsecaseTest {

    @InjectMocks
    private UpdateMenuUsecase usecase;

    @Mock
    private MenuRepository repository;

    @Mock
    private ThumnailFatory thumnailFatory;

    @Test
    void 메뉴_정상_업데이트() {
        Long menuId = 1L;
        UpdateMenuRequestDto request = new UpdateMenuRequestDto("아메리카노", 4000, "COFFEE_BEAN",10L);
        Menu menu = new Menu("라떼",5500, Category.COFFEE_BEAN,mock(Thumnail.class));
        Thumnail thumnail = new Thumnail(2L,"image/jpeg");

    }

    @Test
    void 메뉴_존재하지않으면_예외(){
        Long menuId=1L;

    }

    @Test
    void 카테고리_null이면_예외(){


    }

    @Test
    void 가격_음수면_예외(){

    }

    @Test
    void 썸네일_생성_실패시_예외(){


    }

}
