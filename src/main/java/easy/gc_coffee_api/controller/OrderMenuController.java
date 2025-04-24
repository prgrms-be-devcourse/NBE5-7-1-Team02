package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.OrderRequestDto;
import easy.gc_coffee_api.usecase.order.OrderMenuUserCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderMenuController {

  private final OrderMenuUserCase orderMenuUserCase;

  @PostMapping("/post")
  public ResponseEntity<Long> post(@Valid @RequestBody OrderRequestDto order) {
    return ResponseEntity.ok(orderMenuUserCase.process(order));
  }
}
