package easy.gc_coffee_api.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ThumbnailTests {

    @Test
    void 이미지_타입아닐시_예외(){
        assertThatThrownBy(() -> new Thumbnail(1L,"text/html")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미지_정상_생성(){
        assertDoesNotThrow(() -> new Thumbnail(1L,"image/png"));
    }

}