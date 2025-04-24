package easy.gc_coffee_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMenuRequestDto {
    @NotBlank(message="이름을 입력해주세요.")
    private String name;

    @NotNull(message="가격을 입력해주세요.")
    @Min(value=0, message="가격은 음수일 수 없습니다.")
    private Integer price;

    @NotBlank(message="카테고리를 입력해주세요.")
    private String category;

    private Long imageId;
}
