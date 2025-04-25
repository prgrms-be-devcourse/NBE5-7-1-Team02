package easy.gc_coffee_api.usecase.menu.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MenuDatas {

    private final List<MenuData> menus;

    public MenuDatas(List<MenuData> menus) {
        this.menus = new ArrayList<>(menus);
    }

    public <T> List<T> transform(Function<MenuData, T> function) {
        return menus.stream().map(function).collect(Collectors.toList());
    }
}
