package easy.gc_coffee_api.usecase.order.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderMenuModel {
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
}
