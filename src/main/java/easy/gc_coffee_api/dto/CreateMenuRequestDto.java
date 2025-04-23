package easy.gc_coffee_api.dto;

import easy.gc_coffee_api.entity.common.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMenuRequestDto {

    @NotBlank(message = "menuName은 string이여야 합니다")
    private String menuName;
    @NotBlank
    private String category;
    @NotNull
    @Min(0)
    private Integer price;
    private Long thumnailId;

    public Category getCategory() {
        return Category.findByName(category);
    }
}
