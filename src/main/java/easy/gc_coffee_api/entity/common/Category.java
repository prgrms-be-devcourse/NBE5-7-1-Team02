package easy.gc_coffee_api.entity.common;

import java.util.Arrays;

public enum Category {
    COFFEE_BEAN;

    public static Category findByName(String name) {
        return Arrays.stream(values())
                .filter(category->sameName(category.name(), name))
                .findFirst()
                .orElse(null);
    }

    private static boolean sameName(String categoryName,String name) {
        String replace = categoryName.replace("_", "");
        return replace.equalsIgnoreCase(name) || categoryName.equalsIgnoreCase(name);
    }
}
