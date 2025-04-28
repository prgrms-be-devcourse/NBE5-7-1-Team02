package easy.gc_coffee_api.usecase.menuupdate;

import easy.gc_coffee_api.dto.menu.UpdateMenuRequestDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.exception.menu.InvalidMenuCategoryException;
import easy.gc_coffee_api.exception.menu.InvalidMenuNameException;
import easy.gc_coffee_api.exception.menu.InvalidMenuPriceException;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.usecase.menu.UpdateMenuUseCase;
import easy.gc_coffee_api.usecase.menu.helper.ThumbnailFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateMenuUseCaseTests {

    private UpdateMenuUseCase usecase;

    @Mock
    private MenuRepository repository;

    @Mock
    private ThumbnailFactory thumnailFatory;

    @BeforeEach
    public void setUp(){
        usecase = new UpdateMenuUseCase(repository,thumnailFatory);
    }

    @Test
    void 메뉴_정상_업데이트() {
        Long menuId = 1L;
        Menu menu = new Menu("라떼",5500, Category.COFFEE_BEAN,mock(Thumbnail.class));
        Thumbnail thumbnail = new Thumbnail(10L,"image/jpeg");
        when(repository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.of(menu));
        when(thumnailFatory.create(eq(10L))).thenReturn(thumbnail);

        UpdateMenuRequestDto request = new UpdateMenuRequestDto("아메리카노", 4000, "COFFEE_BEAN",10L);
        usecase.execute(menuId,request);

        assertThat(menu.getCategory()).isEqualTo(Category.COFFEE_BEAN);
        assertThat(menu.getName()).isEqualTo("아메리카노");
        assertThat(menu.getPrice()).isEqualTo(4000);
        assertThat(menu.getThumbnail()).isEqualTo(thumbnail);
    }

    @Test
    void 메뉴_존재하지않으면_예외(){
        Long menuId=1L;
        when(repository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.empty());

        UpdateMenuRequestDto request = new UpdateMenuRequestDto("아메리카노", 4000, "COFFEE_BEAN",10L);
        assertThatThrownBy(()->usecase.execute(menuId,request)).isInstanceOf(MenuNotFoundException.class);
    }

    @Test
    void 카테고리_null이면_예외(){
        Long menuId=1L;
        Menu menu = new Menu("라떼",5500, Category.COFFEE_BEAN,mock(Thumbnail.class));
        Thumbnail thumbnail = new Thumbnail(10L,"image/jpeg");
        UpdateMenuRequestDto request = new UpdateMenuRequestDto("아메리카노", 4000, null,10L);

        when(repository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.of(menu));
        assertThatThrownBy(()->usecase.execute(menuId,request)).isInstanceOf(InvalidMenuCategoryException.class);
    }



    @Test
    void 이름이_null이면_예외() {
        Long menuId=1L;
        Menu menu = new Menu("라떼", 5500, Category.COFFEE_BEAN,mock(Thumbnail.class));
        Thumbnail thumbnail = new Thumbnail(10L,"image/jpeg");
        when(repository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.of(menu));

        UpdateMenuRequestDto request = new UpdateMenuRequestDto(null, 4000, "COFFEE_BEAN",10L);
        assertThatThrownBy(()->usecase.execute(menuId,request)).isInstanceOf(InvalidMenuNameException.class);

    }

    @Test
    void 가격_null이면_예외(){
        Long menuId=1L;
        Menu menu = new Menu("라떼", 5500, Category.COFFEE_BEAN,mock(Thumbnail.class));
        Thumbnail thumbnail = new Thumbnail(10L,"image/jpeg");

        when(repository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.of(menu));
        UpdateMenuRequestDto request = new UpdateMenuRequestDto("커피", null, "COFFEE_BEAN",10L);
        assertThatThrownBy(()->usecase.execute(menuId,request)).isInstanceOf(InvalidMenuPriceException.class);
    }

    @Test
    void 가격_음수면_예외(){
        Long menuId=1L;
        Menu menu = new Menu("라떼", 5500, Category.COFFEE_BEAN,mock(Thumbnail.class));
        Thumbnail thumbnail = new Thumbnail(10L,"image/jpeg");

        when(repository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.of(menu));
        UpdateMenuRequestDto request = new UpdateMenuRequestDto("커피", -4000, "COFFEE_BEAN",10L);
        assertThatThrownBy(()->usecase.execute(menuId,request)).isInstanceOf(InvalidMenuPriceException.class);
    }

//    @Test
//    void 썸네일_생성_실패시_예외(){
//        Long menuId=1L;
//        Menu menu = new Menu("라떼", 5500, Category.COFFEE_BEAN,mock(Thumnail.class));
//        Thumnail thumnail = new Thumnail(10L,"image/jpeg");
//
//        when(repository.findById(eq(menuId))).thenReturn(Optional.of(menu));
//
//
//    }

    @Test
    void 썸네일이_null일때의_기본이미지_테스트(){
        Long menuId=1L;
        Menu menu = new Menu("라떼", 5500, Category.COFFEE_BEAN,mock(Thumbnail.class));
        Thumbnail thumbnail = new Thumbnail(1L,"image/jpeg");

        when(repository.findByIdAndDeletedAtIsNull(eq(menuId))).thenReturn(Optional.of(menu));
        when(thumnailFatory.create(null)).thenReturn(thumbnail);

        UpdateMenuRequestDto request = new UpdateMenuRequestDto("커피", 4000, "COFFEE_BEAN",null);
        usecase.execute(menuId,request);
        assertThat(menu.getThumbnail()).isEqualTo(thumbnail);
    }


}
