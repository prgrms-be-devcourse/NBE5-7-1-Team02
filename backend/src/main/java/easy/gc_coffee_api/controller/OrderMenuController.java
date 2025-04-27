package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.order.CreateOrderRequestDto;
import easy.gc_coffee_api.dto.order.OrderDateRangeDto;
import easy.gc_coffee_api.dto.order.CreateOrderResponseDto;
import easy.gc_coffee_api.dto.order.OrderResponseDto;
import easy.gc_coffee_api.usecase.order.GetOrderListUsecase;
import easy.gc_coffee_api.usecase.order.GetOrderUseCase;
import easy.gc_coffee_api.usecase.order.OrderMenuUseCase;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderMenuController {

  private final OrderMenuUseCase orderMenuUseCase;
  private final GetOrderListUsecase getOrderListUsecase;
  private final ShipOrdersUseCase shipOrdersUseCase;
  private final ShipOrderUseCase shipOrderUseCase;
  private final GetOrderUseCase getOrderUseCase;

  @PostMapping
  public ResponseEntity<CreateOrderResponseDto> post(@Valid @RequestBody CreateOrderRequestDto order) {
    return new ResponseEntity<>(orderMenuUseCase.execute(order), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<OrderDateRangeDto>> getAllOrders() {
    return ResponseEntity.ok(getOrderListUsecase.execute());
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long id) {
    return ResponseEntity.ok(getOrderUseCase.execute(id));
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
