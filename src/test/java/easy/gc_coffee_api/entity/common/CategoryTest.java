package easy.gc_coffee_api.entity.common;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryTest {

    @ParameterizedTest
    @ValueSource(strings = {"COFFEE_BEAN","coffee_bean","coffeeBean","coffeebean"})
    void 카테고리검색(String category) {
        Category coffeeBean = Category.findByName(category);
        assertThat(coffeeBean).isEqualTo(Category.COFFEE_BEAN);
    }

    @ParameterizedTest
    @ValueSource(strings = {"COFFE_BEAN","coffe_bean","coffeBean","coffeebea"})
    void 카테고리검색_실패(String category) {
        assertThatThrownBy(() -> Category.findByName(category)).isInstanceOf(EntityNotFoundException.class);
    }
}