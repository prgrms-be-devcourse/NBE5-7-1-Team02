package easy.gc_coffee_api.entity;

import easy.gc_coffee_api.entity.common.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuTests {

    @Test
    void 썸네일이_존재하는_메뉴() {
        Menu menu = new Menu("name", 1000, Category.COFFEE_BEAN, new Thumbnail(1L, "image/jpeg"));

        assertThat(menu.hasThumbNail()).isTrue();
    }

    @Test
    void 썸네일이_존재하지_않는_메뉴() {
        Menu menu = new Menu("name", 1000, Category.COFFEE_BEAN, new Thumbnail());

        assertThat(menu.hasThumbNail()).isFalse();
    }

    @Test
    void 썸네일이_null인_메뉴() {
        Menu menu = new Menu("name", 1000, Category.COFFEE_BEAN, null);

        assertThat(menu.hasThumbNail()).isFalse();
    }

    @Test
    void 썸네일이_존재하는_메뉴_썸네일아이디() {
        Long thumbNailId = 1L;
        Menu menu = new Menu("name", 1000, Category.COFFEE_BEAN, new Thumbnail(thumbNailId, "image/jpeg"));

        assertThat(menu.getThumbnailId()).isEqualTo(thumbNailId);
    }

    @Test
    void 썸네일이_존재하지_않는_메뉴_썸네일아이디() {
        Menu menu = new Menu("name", 1000, Category.COFFEE_BEAN, new Thumbnail());

        assertThat(menu.getThumbnailId()).isNull();
    }

    @Test
    void 썸네일이_null인_메뉴_썸네일아이디() {
        Menu menu = new Menu("name", 1000, Category.COFFEE_BEAN, null);

        assertThat(menu.getThumbnailId()).isNull();
    }

}