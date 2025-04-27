package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.order.*;
import easy.gc_coffee_api.usecase.order.GetOrderListUsecase;
import easy.gc_coffee_api.usecase.order.GetOrderUseCase;
import easy.gc_coffee_api.usecase.order.OrderMenuUseCase;
import easy.gc_coffee_api.usecase.order.ShipOrderUseCase;
import easy.gc_coffee_api.usecase.order.ShipOrdersUseCase;
import easy.gc_coffee_api.usecase.order.CreateOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderMenuController {

  private final CreateOrderUseCase createOrderUseCase;
  private final GetOrderListUsecase getOrderListUsecase;
  private final ShipOrdersUseCase shipOrdersUseCase;
  private final ShipOrderUseCase shipOrderUseCase;
  private final GetOrderUseCase getOrderUseCase;

  @PostMapping
  public ResponseEntity<OrderResponseDto> post(@Valid @RequestBody CreateOrderRequestDto order) {
    return new ResponseEntity<>(createOrderUseCase.execute(order), HttpStatus.CREATED);
  }

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<OrderDateRangeDto>> getAllOrders() {
    return ResponseEntity.ok(getOrderListUsecase.execute());
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long id, @RequestParam String email) {
    return ResponseEntity.ok(getOrderUseCase.execute(id, email));
  }

  @PatchMapping("/ship")
  public ResponseEntity<Map<String, Boolean>> ship(@RequestBody Map<String, List<Long>> request) {
    List<Long> ids = request.get("ids");
    shipOrdersUseCase.execute(ids);

    Map<String, Boolean> response = new HashMap<>();
    response.put("result", true);
    return ResponseEntity.ok(response);
  }


  @PatchMapping("/{orderId}/ship")
  public ResponseEntity<Map<String, Boolean>> ship(@PathVariable Long orderId) {
    shipOrderUseCase.execute(orderId);
    Map<String, Boolean> response = new HashMap<>();
    response.put("result", true);
    return ResponseEntity.ok(response);
  }

}
