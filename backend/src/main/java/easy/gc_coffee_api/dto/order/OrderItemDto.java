package easy.gc_coffee_api.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
  @NotNull(message = "메뉴 아이디가 필요합니다.")
  private Long menuId;

  @NotNull(message = "수량을 입력해주세요.")
  @Min(value = 1 , message = "수량은 최소 1개 이상이여야합니다.")
  private Integer quantity;

}
