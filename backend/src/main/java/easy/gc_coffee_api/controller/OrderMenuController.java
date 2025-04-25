package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.OrderRequestDto;
import easy.gc_coffee_api.dto.OrderListResponseDto;
import easy.gc_coffee_api.usecase.order.OrderMenuUserCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderMenuController {

  private final OrderMenuUserCase orderMenuUserCase;

  @PostMapping("/orders")
  public ResponseEntity<Long> post(@Valid @RequestBody OrderRequestDto order) {
    return new ResponseEntity<>(orderMenuUserCase.execute(order), HttpStatus.CREATED);
  }

  @GetMapping("/orders")
  public ResponseEntity<List<OrderListResponseDto>> getAllOrders() {
    return ResponseEntity.ok(adminOrderListUsecase.execute());
  }
}
