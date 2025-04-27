package easy.gc_coffee_api.entity;

import easy.gc_coffee_api.entity.common.BaseDateEntity;
import easy.gc_coffee_api.entity.common.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Embedded
    private Address address;
    @Setter
    private Integer totalPrice;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    public Orders(Long id, String email, Address address, Integer totalPrice) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.PENDING;
    }

    @Builder
    public Orders(String email, Address address, Integer totalPrice) {
        this(null, email, address, totalPrice);
    }

    public void ship(){
        this.status = OrderStatus.SHIPPED;
    }
}
