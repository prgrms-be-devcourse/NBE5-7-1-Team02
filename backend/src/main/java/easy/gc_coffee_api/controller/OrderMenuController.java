package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.order.*;
import easy.gc_coffee_api.usecase.order.GetOrderListUsecase;
import easy.gc_coffee_api.usecase.order.GetOrderUseCase;
import easy.gc_coffee_api.usecase.order.CreateOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderMenuController {

  private final CreateOrderUseCase createOrderUseCase;
  private final GetOrderListUsecase getOrderListUsecase;
  private final GetOrderUseCase getOrderUseCase;

  @PostMapping
  public ResponseEntity<OrderResponseDto> post(@Valid @RequestBody CreateOrderRequestDto order) {
    return new ResponseEntity<>(createOrderUseCase.execute(order), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<OrderDateRangeDto>> getAllOrders() {
    return ResponseEntity.ok(getOrderListUsecase.execute());
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long id, @RequestParam String email) {
    return ResponseEntity.ok(getOrderUseCase.execute(id, email));
  }
}
