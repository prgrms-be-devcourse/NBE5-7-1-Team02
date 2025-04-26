package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.OrderRequestDto;
import easy.gc_coffee_api.dto.OrderResponseDto;
import easy.gc_coffee_api.exception.GCException;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.usecase.order.OrderMenuUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderMenuController {

  private final OrderMenuUseCase orderMenuUseCase;

  @PostMapping("/orders")
  public ResponseEntity<OrderResponseDto> post(@Valid @RequestBody OrderRequestDto order) {
      OrderResponseDto responseDto = orderMenuUseCase.execute(order);
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }
}
