package easy.gc_coffee_api.usecase.order;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.gc_coffee_api.dto.AddressDto;
import easy.gc_coffee_api.dto.order.OrderItemDto;
import easy.gc_coffee_api.dto.order.CreateOrderRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.FileRepository;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class OrderMenuUseCaseTest_ERROR {

  private static Validator validator;

  @Autowired private OrderMenuUseCase service;
  @Autowired private FileRepository       fileRepo;
  @Autowired private MenuRepository       menuRepo;
  @Autowired private OrderRepository      orderRepo;
  @Autowired private OrderMenuRepository  orderMenuRepo;
  @Autowired private ObjectMapper         objectMapper;

  private AddressDto      validAddress;
  private List<OrderItemDto> validItems;

  @BeforeAll
  static void initValidator() {
    ValidatorFactory f = Validation.buildDefaultValidatorFactory();
    validator = f.getValidator();
  }

  @BeforeEach
  void setUp() throws Exception {
    // --- 테스트용 File, Menu 준비 (영속 상태) ---
    Constructor<File> ctor = File.class.getDeclaredConstructor();
    ctor.setAccessible(true);
    File realFile = ctor.newInstance();
    ReflectionTestUtils.setField(realFile, "mimetype", "image/png");
    ReflectionTestUtils.setField(realFile, "url",      "/dummy.png");
    realFile = fileRepo.save(realFile);

    Thumbnail th1 = new Thumbnail(realFile.getId()),
        th2 = new Thumbnail(realFile.getId());

    Menu m1 = menuRepo.save(new Menu(null, "m1", 1000, Category.COFFEE_BEAN, th1));
    Menu m2 = menuRepo.save(new Menu(null, "m2", 2000, Category.COFFEE_BEAN, th2));

    validAddress = AddressDto.builder()
        .address("서울 강남구")
        .zipCode("12345")
        .build();

    validItems = List.of(
        new OrderItemDto(m1.getId(), 1),
        new OrderItemDto(m2.getId(), 2)
    );

    // DB 초기화
    orderMenuRepo.deleteAll();
    orderRepo.deleteAll();
  }

  //--- 1. DTO 검증 테스트들 ------------------------------------
  @Test
  @DisplayName("잘못된 이메일 형식이면 ConstraintViolation 발생 및 오류 출력")
  void invalidEmail() {
    CreateOrderRequestDto dto = CreateOrderRequestDto.builder()
        .email("wrong-format")
        .addressdto(validAddress)
        .items(validItems)
        .build();

    Set<ConstraintViolation<CreateOrderRequestDto>> errs = validator.validate(dto);

    // ↓↓ 실제 에러 출력 ↓↓
    errs.forEach(v -> {
      String field    = v.getPropertyPath().toString();
      String anno     = v.getConstraintDescriptor()
          .getAnnotation()
          .annotationType()
          .getSimpleName();
      String message  = v.getMessage();
      System.out.printf("Error - field: %-10s  [%s] %s%n", field, anno, message);
    });

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream()
        .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
  }

  @Test
  @DisplayName("우편번호가 숫자 아니면 ConstraintViolation 발생 및 오류 출력")
  void invalidZipCode() {
    AddressDto badAddr = AddressDto.builder()
        .address("서울시")
        .zipCode("ABCDE")
        .build();

    Set<ConstraintViolation<AddressDto>> errs = validator.validate(badAddr);

    errs.forEach(v -> {
      String field   = v.getPropertyPath().toString();
      String anno    = v.getConstraintDescriptor()
          .getAnnotation()
          .annotationType()
          .getSimpleName();
      String message = v.getMessage();
      System.out.printf("Error - field: %-10s  [%s] %s%n", field, anno, message);
    });

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream()
        .anyMatch(v -> v.getPropertyPath().toString().equals("zipCode")));
  }

  @Test
  @DisplayName("빈 주문 항목 리스트면 ConstraintViolation 발생 및 오류 출력")
  void emptyItems() {
    CreateOrderRequestDto dto = CreateOrderRequestDto.builder()
        .email("a@b.com")
        .addressdto(validAddress)
        .items(List.of())
        .build();

    Set<ConstraintViolation<CreateOrderRequestDto>> errs = validator.validate(dto);

    errs.forEach(v -> {
      String field   = v.getPropertyPath().toString();
      String anno    = v.getConstraintDescriptor()
          .getAnnotation()
          .annotationType()
          .getSimpleName();
      String message = v.getMessage();
      System.out.printf("Error - field: %-10s  [%s] %s%n", field, anno, message);
    });

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream()
        .anyMatch(v -> v.getPropertyPath().toString().equals("items")));
  }

  @Test
  @DisplayName("OrderItemDto: 메뉴ID 누락이면 ConstraintViolation 발생 및 오류 출력")
  void itemNullMenuId() {
    OrderItemDto item = new OrderItemDto(null, 1);
    Set<ConstraintViolation<OrderItemDto>> errs = validator.validate(item);

    errs.forEach(v -> {
      String field   = v.getPropertyPath().toString();
      String anno    = v.getConstraintDescriptor()
          .getAnnotation()
          .annotationType()
          .getSimpleName();
      String message = v.getMessage();
      System.out.printf("Error - field: %-10s  [%s] %s%n", field, anno, message);
    });

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream()
        .anyMatch(v -> v.getPropertyPath().toString().equals("menuId")));
  }

  @Test
  @DisplayName("OrderItemDto: 수량 0 이하면 ConstraintViolation 발생 및 오류 출력")
  void itemQuantityTooLow() {
    OrderItemDto item = new OrderItemDto(1L, 0);
    Set<ConstraintViolation<OrderItemDto>> errs = validator.validate(item);

    errs.forEach(v -> {
      String field   = v.getPropertyPath().toString();
      String anno    = v.getConstraintDescriptor()
          .getAnnotation()
          .annotationType()
          .getSimpleName();
      String message = v.getMessage();
      System.out.printf("Error - field: %-10s  [%s] %s%n", field, anno, message);
    });

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream()
        .anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
  }
  //--- 2. 서비스 예외 테스트 ----------------------------------

  @Test
  @DisplayName("존재하지 않는 메뉴 ID로 process 호출 시 예외 메시지 출력")
  void menuNotFound_printException() {
    // Given: 메뉴가 없는 ID를 담은 DTO
    CreateOrderRequestDto dto = CreateOrderRequestDto.builder()
        .email("ok@ok.com")
        .addressdto(validAddress)
        .items(List.of(new OrderItemDto(9999L, 1)))
        .build();

    // When & Then: IllegalArgumentException 이 던져져야 하고, 메시지를 찍어본다
    IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> service.execute(dto)
    );

    // 콘솔에 실제 예외 메시지 출력
    System.out.println("▶ 발생한 예외 메시지: " + ex.getMessage());

    // 메시지가 우리가 기대한 문자열을 포함하는지 단언
    assertTrue(
        ex.getMessage().contains("존재하지 않는 메뉴 ID"),
        "예외 메시지에 '존재하지 않는 메뉴 ID' 가 포함되어야 합니다."
    );
  }
}