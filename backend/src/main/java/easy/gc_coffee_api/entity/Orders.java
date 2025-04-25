package easy.gc_coffee_api.entity;

import easy.gc_coffee_api.entity.common.BaseDateEntity;
import easy.gc_coffee_api.usecase.order.ShippingStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;

    @Embedded
    private Address address;
    @Setter
    private Integer totalPrice;

    public Orders(Long id, String email, Address address, Integer totalPrice) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.totalPrice = totalPrice;
        this.shippingStatus = ShippingStatus.PENDING;
    }

    @Builder
    public Orders(String email, Address address, Integer totalPrice) {
        this(null, email, address, totalPrice);
    }

    public void ship() {
        this.shippingStatus = ShippingStatus.SHIPPING;
    }
}
