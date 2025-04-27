package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.OrderRequestDto;
import easy.gc_coffee_api.dto.OrderListResponseDto;
import easy.gc_coffee_api.exception.GCException;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.usecase.order.GetOrderListUsecase;
import easy.gc_coffee_api.usecase.order.OrderMenuUserCase;
import easy.gc_coffee_api.usecase.order.ShipOrderUseCase;
import easy.gc_coffee_api.usecase.order.ShipOrdersUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderMenuController {

  private final OrderMenuUserCase orderMenuUserCase;
  private final GetOrderListUsecase getOrderListUsecase;
  private final ShipOrdersUseCase shipOrdersUseCase;
  private final ShipOrderUseCase shipOrderUseCase;

  @PostMapping("/orders")
  public ResponseEntity<Long> post(@Valid @RequestBody OrderRequestDto order) {
    return new ResponseEntity<>(orderMenuUserCase.execute(order), HttpStatus.CREATED);
  }

  @GetMapping("/orders")
  public ResponseEntity<List<OrderListResponseDto>> getAllOrders() {
    return ResponseEntity.ok(getOrderListUsecase.execute());
  }

  @PatchMapping("/orders/ship")
  public ResponseEntity<Map<String, Boolean>> ship(@RequestBody List<Long> ids) {
    shipOrdersUseCase.execute(ids);
    Map<String, Boolean> response = new HashMap<>();
    response.put("result", true);
    return ResponseEntity.ok(response);
  }


  @PatchMapping("/orders/{orderId}/ship")
  public ResponseEntity<Map<String, Boolean>> ship(@PathVariable Long orderId) {
    shipOrderUseCase.execute(orderId);
    Map<String, Boolean> response = new HashMap<>();
    response.put("result", true);
    return ResponseEntity.ok(response);
  }

}
