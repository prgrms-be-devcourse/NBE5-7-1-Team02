package easy.gc_coffee_api.entity.common;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @ParameterizedTest
    @ValueSource(strings = {"COFFEE_BEAN","coffee_bean","coffeeBean","coffeebean"})
    void 카테고리검색(String category) {
        Category coffeeBean = Category.findByName(category);
        assertThat(coffeeBean).isEqualTo(Category.COFFEE_BEAN);
    }
}