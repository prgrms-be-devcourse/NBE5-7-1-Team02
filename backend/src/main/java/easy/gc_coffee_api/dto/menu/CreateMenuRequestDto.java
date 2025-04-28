package easy.gc_coffee_api.dto.menu;

import easy.gc_coffee_api.entity.common.Category;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMenuRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String menuName;
    @NotBlank(message = "카테고리를 입력해주세요.")
    private String category;
    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 0,message = "가격은 0이상이여야 합니다.")
    private Integer price;
    private Long thumbnailId;

    public Category getCategory() throws EntityNotFoundException {
        return Category.findByName(category);
    }
}
