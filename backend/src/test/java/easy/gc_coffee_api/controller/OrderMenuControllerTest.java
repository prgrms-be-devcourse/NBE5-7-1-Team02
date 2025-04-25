package easy.gc_coffee_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.gc_coffee_api.dto.AddressDto;
import easy.gc_coffee_api.dto.OrderItemDto;
import easy.gc_coffee_api.dto.OrderRequestDto;
import easy.gc_coffee_api.dto.OrderResponseDto;
import easy.gc_coffee_api.exception.GCException;
import easy.gc_coffee_api.usecase.order.OrderMenuUseCase;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderMenuControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private OrderMenuUseCase orderMenuUseCase;



  @Test
  @DisplayName("POST /orders - 성공: 201 Created, OrderResponseDto 반환")
  void postOrderSuccess() throws Exception {
    // Long → OrderResponseDto 를 반환하도록 수정
    given(orderMenuUseCase.execute(any(OrderRequestDto.class)))
        .willReturn(OrderResponseDto.builder()
            .orderId(42L)
            .build()
        );

    OrderRequestDto dto = OrderRequestDto.builder()
        .email("user@example.com")
        .addressdto(new AddressDto("서울시 강남구", "12345"))
        .items(List.of(new OrderItemDto(1L, 2)))
        .build();

    mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andDo(print())
        .andExpect(status().isCreated())
        // content() 검증도 JSON 형태로 바꿔야 합니다
        .andExpect(content().json("{\"orderId\":42}"));
  }

  @Nested
  @DisplayName("검증 실패 케이스: 400 Bad Request")
  class ValidationErrors {

    @Test
    @DisplayName("이메일 형식 오류 (빈 문자열 또는 잘못된 형식)")
    void invalidEmail() throws Exception {
      OrderRequestDto dto = OrderRequestDto.builder()
          .email("동해물")  // invalid
          .addressdto(new AddressDto("유효한 주소", "12345"))
          .items(List.of(new OrderItemDto(1L, 1)))
          .build();

      mockMvc.perform(post("/orders")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(dto)))
          .andDo(print())
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message")
              .value("이메일에 '@'가 없거나 형식이 잘못되었습니다."))
          .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    @DisplayName("숫자 아닌 우편번호")
    void invalidZipCode() throws Exception {
      OrderRequestDto dto = OrderRequestDto.builder()
          .email("user@example.com")
          .addressdto(new AddressDto("유효한 주소", "ABCDE"))
          .items(List.of(new OrderItemDto(1L, 1)))
          .build();

      mockMvc.perform(post("/orders")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(dto)))
          .andDo(print())
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message")
              .value("우편번호는 숫자만 입력 가능합니다."))
          .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    @DisplayName("빈 아이템 리스트")
    void emptyItems() throws Exception {
      OrderRequestDto dto = OrderRequestDto.builder()
          .email("user@example.com")
          .addressdto(new AddressDto("유효한 주소", "12345"))
          .items(List.of())
          .build();

      mockMvc.perform(post("/orders")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(dto)))
          .andDo(print())
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message")
              .value("최소 하나 이상의 주문 항목이 필요합니다."))
          .andExpect(jsonPath("$.statusCode").value(400));
    }
  }

  @Test
  @DisplayName("존재하지 않는 메뉴 ID: 404 Not Found")
  void menuNotFound() throws Exception {
    given(orderMenuUseCase.execute(any(OrderRequestDto.class)))
        .willAnswer(invocation -> {
          // GCException(String message, Throwable cause, Integer code) 생성자 사용
          throw new GCException(
              "존재하지 않는 메뉴 ID: 9999",
              new RuntimeException("cause"),
              404
          );
        });

    OrderRequestDto dto = OrderRequestDto.builder()
        .email("user@example.com")
        .addressdto(new AddressDto("서울시 강남구", "12345"))
        .items(List.of(new OrderItemDto(9999L, 1)))
        .build();

    mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message")
            .value("존재하지 않는 메뉴 ID: 9999"))
        .andExpect(jsonPath("$.statusCode").value(404));
  }
}