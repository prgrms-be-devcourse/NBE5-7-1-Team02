package easy.gc_coffee_api.usecase.order;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.gc_coffee_api.dto.AddressDto;
import easy.gc_coffee_api.dto.OrderItemDto;
import easy.gc_coffee_api.dto.OrderRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
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
import easy.gc_coffee_api.entity.Thumnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.FileRepository;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;

@SpringBootTest
class OrderMenuUserCaseTest_ERROR {

  private static Validator validator;

  @Autowired private OrderMenuUserCase    service;
  @Autowired private FileRepository        fileRepo;
  @Autowired private MenuRepository        menuRepo;
  @Autowired private OrderRepository       orderRepo;
  @Autowired private OrderMenuRepository   orderMenuRepo;
  @Autowired private ObjectMapper          objectMapper;

  private AddressDto validAddress;
  private List<OrderItemDto> validItems;

  @BeforeAll
  static void initValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @BeforeEach
  void setUp() {

    orderMenuRepo.deleteAll();
    orderRepo.deleteAll();
    menuRepo.deleteAll();
    fileRepo.deleteAll();


    File realFile = new File("image/png", "/dummy.png");
    realFile = fileRepo.save(realFile);


    Thumnail th1 = new Thumnail(realFile.getId(), realFile.getMimetype());
    Thumnail th2 = new Thumnail(realFile.getId(), realFile.getMimetype());


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
  }


  @Test
  @DisplayName("잘못된 이메일 형식이면 ConstraintViolation 발생 및 오류 출력")
  void invalidEmail() {
    OrderRequestDto dto = OrderRequestDto.builder()
        .email("wrong-format")
        .addressdto(validAddress)
        .items(validItems)
        .build();

    Set<ConstraintViolation<OrderRequestDto>> errs = validator.validate(dto);
    errs.forEach(v -> System.out.printf(
        "Error - field: %-10s [%s] %s%n",
        v.getPropertyPath(),
        v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
        v.getMessage()
    ));

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
  }

  @Test
  @DisplayName("우편번호가 숫자 아니면 ConstraintViolation 발생 및 오류 출력")
  void invalidZipCode() {
    AddressDto badAddr = AddressDto.builder()
        .address("서울시")
        .zipCode("ABCDE")
        .build();

    Set<ConstraintViolation<AddressDto>> errs = validator.validate(badAddr);
    errs.forEach(v -> System.out.printf(
        "Error - field: %-10s [%s] %s%n",
        v.getPropertyPath(),
        v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
        v.getMessage()
    ));

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream().anyMatch(v -> v.getPropertyPath().toString().equals("zipCode")));
  }

  @Test
  @DisplayName("빈 주문 항목 리스트면 ConstraintViolation 발생 및 오류 출력")
  void emptyItems() {
    OrderRequestDto dto = OrderRequestDto.builder()
        .email("a@b.com")
        .addressdto(validAddress)
        .items(List.of())
        .build();

    Set<ConstraintViolation<OrderRequestDto>> errs = validator.validate(dto);
    errs.forEach(v -> System.out.printf(
        "Error - field: %-10s [%s] %s%n",
        v.getPropertyPath(),
        v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
        v.getMessage()
    ));

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream().anyMatch(v -> v.getPropertyPath().toString().equals("items")));
  }

  @Test
  @DisplayName("OrderItemDto: 메뉴ID 누락이면 ConstraintViolation 발생 및 오류 출력")
  void itemNullMenuId() {
    OrderItemDto item = new OrderItemDto(null, 1);
    Set<ConstraintViolation<OrderItemDto>> errs = validator.validate(item);
    errs.forEach(v -> System.out.printf(
        "Error - field: %-10s [%s] %s%n",
        v.getPropertyPath(),
        v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
        v.getMessage()
    ));

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream().anyMatch(v -> v.getPropertyPath().toString().equals("menuId")));
  }

  @Test
  @DisplayName("OrderItemDto: 수량 0 이하면 ConstraintViolation 발생 및 오류 출력")
  void itemQuantityTooLow() {
    OrderItemDto item = new OrderItemDto(1L, 0);
    Set<ConstraintViolation<OrderItemDto>> errs = validator.validate(item);
    errs.forEach(v -> System.out.printf(
        "Error - field: %-10s [%s] %s%n",
        v.getPropertyPath(),
        v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
        v.getMessage()
    ));

    assertFalse(errs.isEmpty());
    assertTrue(errs.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
  }


  @Test
  @DisplayName("존재하지 않는 메뉴 ID로 process 호출 시 예외 메시지 출력")
  void menuNotFound_printException() {
    OrderRequestDto dto = OrderRequestDto.builder()
        .email("ok@ok.com")
        .addressdto(validAddress)
        .items(List.of(new OrderItemDto(9999L, 1)))
        .build();

    IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> service.process(dto)
    );

    System.out.println("▶ 발생한 예외 메시지: " + ex.getMessage());
    assertTrue(ex.getMessage().contains("존재하지 않는 메뉴 ID"));
  }
}