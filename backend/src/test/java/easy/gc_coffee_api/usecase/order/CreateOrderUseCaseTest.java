package easy.gc_coffee_api.usecase.order;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import easy.gc_coffee_api.dto.AddressDto;
import easy.gc_coffee_api.dto.order.OrderItemDto;
import easy.gc_coffee_api.dto.order.CreateOrderRequestDto;
import easy.gc_coffee_api.dto.order.CreateOrderResponseDto;
import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.OrderMenu;
import easy.gc_coffee_api.entity.Orders;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.FileRepository;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.repository.OrderMenuRepository;
import easy.gc_coffee_api.repository.OrderRepository;

import java.lang.reflect.Constructor;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CreateOrderUseCaseTest {

    @Autowired
    private CreateOrderUseCase createOrderUseCase;
    @Autowired
    private OrderRepository       orderRepository;
    @Autowired
    private OrderMenuRepository   orderMenuRepository;
    @Autowired
    private MenuRepository        menuRepository;
    @Autowired
    private FileRepository        fileRepository;
    @Autowired
    private ObjectMapper          objectMapper;

    private AddressDto            addressDto_Test;
    private List<OrderItemDto>    test_Items;

    @BeforeEach
    void setUp() throws Exception {
        // 1) protected 기본 생성자로 File 인스턴스 생성
        Constructor<File> ctor = File.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        File realFile = ctor.newInstance();

        // 2) 리플렉션으로 필드 주입
        ReflectionTestUtils.setField(realFile, "mimetype", "image/png");
        ReflectionTestUtils.setField(realFile, "url",      "/dummy.png");

        // 3) DB에 저장하여 영속화
        realFile = fileRepository.save(realFile);

        // 4) Embeddable Thumnail 생성
        Thumbnail thumbnail1 = new Thumbnail(realFile.getId());
        Thumbnail thumbnail2 = new Thumbnail(realFile.getId());

        // 5) Menu 엔티티 두 개 생성 및 저장
        Menu menuTest1 = menuRepository.save(new Menu(
            null,
            "test1",
            20_000,
            Category.COFFEE_BEAN,
            thumbnail1
        ));
        Menu menuTest2 = menuRepository.save(new Menu(
            null,
            "test2",
            30_000,
            Category.COFFEE_BEAN,
            thumbnail2
        ));

        // 6) AddressDto 준비
        addressDto_Test = AddressDto.builder()
            .address("청와대")
            .zipCode("0000")
            .build();

        // 7) OrderItemDto 리스트 준비
        test_Items = List.of(
            new OrderItemDto(menuTest1.getId(), 2),
            new OrderItemDto(menuTest2.getId(), 3)
        );
    }

    @Test
    @DisplayName("Request DTO 및 Persisted Entities를 JSON으로 출력하고 검증한다")
    @Transactional
    void test_printRequestAndPersisted() throws Exception {
        // --- Given: Request DTO 생성 ---
        CreateOrderRequestDto requestDto = CreateOrderRequestDto.builder()
            .email("test@test.com")
            .addressdto(addressDto_Test)
            .items(test_Items)
            .build();

        // 1) Request DTO → JSON 출력
        String reqJson = objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(requestDto);
        System.out.println("▶ Request JSON:\n" + reqJson);

        // --- When: 서비스 호출 ---
        CreateOrderResponseDto responseDto = createOrderUseCase.execute(requestDto);

        // --- Then: Orders 조회 & JSON 출력 ---
        Orders savedOrder = orderRepository.findById(responseDto.getOrderId())
            .orElseThrow(() -> new IllegalArgumentException("Order not found: " + responseDto.getOrderId()));

        // JSON으로 직렬화 (엔티티 직접 변환)
        JsonNode orderNode = objectMapper.convertValue(savedOrder, JsonNode.class);
        String orderJson = objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(orderNode);
        System.out.println("▶ Persisted Orders JSON:\n" + orderJson);

        // Assertions on Orders
        assertEquals("test@test.com", savedOrder.getEmail());
        assertEquals("0000",          savedOrder.getAddress().getZipCode());
        assertEquals(20_000*2 + 30_000*3, savedOrder.getTotalPrice());

        // --- Then: OrderMenu 조회 & JSON 출력 ---
        List<OrderMenu> savedMenus = orderMenuRepository.findByOrdersId(responseDto.getOrderId());

        // 강제 초기화: lazy 로딩된 menu 프록시 안 깨지게
        savedMenus.forEach(om -> om.getMenu().getName());

        JsonNode menusNode = objectMapper.convertValue(savedMenus, JsonNode.class);
        String menusJson = objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(menusNode);
        System.out.println("▶ Persisted OrderMenu JSON:\n" + menusJson);

        // Assertions on OrderMenu
        assertEquals(2, savedMenus.size());
        OrderMenu first = savedMenus.stream()
            .filter(om -> om.getMenu().getId().equals(test_Items.get(0).getMenuId()))
            .findFirst().orElseThrow();
        assertEquals(2,      first.getQuantity());
        assertEquals(20_000, first.getPrice());
        assertEquals("test1", first.getName());
    }
}