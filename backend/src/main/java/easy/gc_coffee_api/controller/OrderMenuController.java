package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.OrderRequestDto;
import easy.gc_coffee_api.exception.GCException;
import easy.gc_coffee_api.usecase.order.OrderMenuUserCase;
import easy.gc_coffee_api.usecase.order.OrderShippingUseCase;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderMenuController {

    private final OrderMenuUserCase orderMenuUserCase;
    private final OrderShippingUseCase orderShippingUseCase;

    @PostMapping("/orders")
    public ResponseEntity<Long> post(@Valid @RequestBody OrderRequestDto order) {
        return new ResponseEntity<>(orderMenuUserCase.execute(order), HttpStatus.CREATED);
    }

    @PatchMapping("/orders/{orderId}/ship")
    public ResponseEntity<Void> ship(@PathVariable Long orderId) {
        try {
            orderShippingUseCase.execute(orderId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new GCException("order를 찾을 수 없습니다.", e, 400);
        }
    }
}
