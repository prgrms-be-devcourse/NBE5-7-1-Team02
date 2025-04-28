package easy.gc_coffee_api.entity.common;

import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;

public enum Category {
    COFFEE_BEAN, DRINK, DESSERT;

    public static Category findByName(String name) {
        return Arrays.stream(values())
                .filter(category->sameName(category.name(), name))
                .findFirst()
                .orElseThrow(()->new EntityNotFoundException("카테고리를 찾을 수 없습니다."));
    }

    private static boolean sameName(String categoryName,String name) {
        String replace = categoryName.replace("_", "");
        return replace.equalsIgnoreCase(name) || categoryName.equalsIgnoreCase(name);
    }
}
