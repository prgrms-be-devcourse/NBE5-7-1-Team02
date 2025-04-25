package easy.gc_coffee_api.entity;

import easy.gc_coffee_api.entity.common.BaseDateEntity;
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
    @Embedded
    private Address address;
    @Setter
    private Integer totalPrice;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder
    public Orders(String email, Address address, Integer totalPrice) {
        this.email = email;
        this.address = address;
        this.totalPrice = totalPrice;
    }
}
