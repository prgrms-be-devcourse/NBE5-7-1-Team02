package easy.gc_coffee_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class AddressDto {

  @NotBlank(message = "주소를 입력하세요")
  private String address;

  @NotBlank(message = "우편번호는 필수입니다.")
  @Pattern(
      regexp = "^[0-9]+$",
      message = "우편번호는 숫자만 입력 가능합니다."
  )
  private String zipCode;


}
