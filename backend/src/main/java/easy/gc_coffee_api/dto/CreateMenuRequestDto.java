package easy.gc_coffee_api.dto;

import easy.gc_coffee_api.entity.common.Category;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMenuRequestDto {

    @NotBlank(message = "menuName은 빈값이 아니여야합니다")
    private String menuName;
    @NotBlank(message = "category는 빈값이 아니여야합니다.")
    private String category;
    @NotNull(message = "price는 필 수 입니다.")
    @Min(value = 0,message = "가격은 0이상이여야 합니다.")
    private Integer price;
    private Long thumbnailId;

    public Category getCategory() throws EntityNotFoundException {
        return Category.findByName(category);
    }
}
