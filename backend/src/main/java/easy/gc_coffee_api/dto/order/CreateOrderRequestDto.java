package easy.gc_coffee_api.dto.order;


import easy.gc_coffee_api.dto.AddressDto;
import easy.gc_coffee_api.entity.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

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
public class CreateOrderRequestDto {

    @NotBlank(message = "이메일을 입력하세요.")
    @Pattern(
            regexp = "^[^@\\s]+@[^@\\s]+$",
            message = "이메일에 '@'가 없거나 형식이 잘못되었습니다."
    )
    private String email;

    @Valid
    private AddressDto addressdto;

    @NotEmpty(message = "최소 하나 이상의 주문 항목이 필요합니다.")
    private List<OrderItemDto> items;

    public Address getAddress() {
        return addressdto.toAddress();
    }
}
