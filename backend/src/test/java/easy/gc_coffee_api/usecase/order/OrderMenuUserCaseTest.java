package easy.gc_coffee_api.usecase.order;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import easy.gc_coffee_api.dto.AddressDto;
import easy.gc_coffee_api.dto.OrderItemDto;
import easy.gc_coffee_api.dto.OrderRequestDto;
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

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderMenuUserCaseTest {

    @Autowired
    private OrderMenuUserCase orderMenuUserCase;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMenuRepository orderMenuRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private AddressDto addressDto;
    private List<OrderItemDto> testItems;

    @BeforeEach
    void setUp() {

        File realFile = new File("image/png", "/dummy.png");
        realFile = fileRepository.save(realFile);


        Thumbnail thumbnail1 = new Thumbnail(realFile.getId(), realFile.getMimetype());
        Thumbnail thumbnail2 = new Thumbnail(realFile.getId(), realFile.getMimetype());


        Menu menuTest1 = new Menu(null, "test1", 20_000, Category.COFFEE_BEAN, thumbnail1);
        Menu menuTest2 = new Menu(null, "test2", 30_000, Category.COFFEE_BEAN, thumbnail2);
        menuTest1 = menuRepository.save(menuTest1);
        menuTest2 = menuRepository.save(menuTest2);


        addressDto = AddressDto.builder()
                .address("청와대")
                .zipCode("0000")
                .build();


        testItems = List.of(
                new OrderItemDto(menuTest1.getId(), 2),
                new OrderItemDto(menuTest2.getId(), 3)
        );


        orderMenuRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("Request DTO 및 Persisted Entities를 JSON으로 출력하고 검증한다")
    @Transactional
    void testPrintRequestAndPersisted() throws Exception {
        // Given
        OrderRequestDto requestDto = OrderRequestDto.builder()
                .email("test@test.com")
                .addressdto(addressDto)
                .items(testItems)
                .build();

        String reqJson = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(requestDto);
        System.out.println("▶ Request JSON:\n" + reqJson);
        // When
        Long savedOrderId = orderMenuUserCase.execute(requestDto);

        Orders savedOrder = orderRepository.findById(savedOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + savedOrderId));
        JsonNode orderNode = objectMapper.convertValue(savedOrder, JsonNode.class);
        String orderJson = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(orderNode);
        System.out.println("▶ Persisted Orders JSON:\n" + orderJson);

        assertEquals("test@test.com", savedOrder.getEmail());
        assertEquals("0000", savedOrder.getAddress().getZipCode());
        assertEquals(20_000 * 2 + 30_000 * 3, savedOrder.getTotalPrice());

        // Then: OrderMenu 조회 및 JSON 출력
        List<OrderMenu> savedMenus = orderMenuRepository.findByOrdersId(savedOrderId);
        savedMenus.forEach(om -> om.getMenu().getName());
        JsonNode menusNode = objectMapper.convertValue(savedMenus, JsonNode.class);
        String menusJson = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(menusNode);
        System.out.println("▶ Persisted OrderMenu JSON:\n" + menusJson);

        assertEquals(2, savedMenus.size());
        OrderMenu first = savedMenus.stream()
                .filter(om -> om.getMenu().getId().equals(testItems.get(0).getMenuId()))
                .findFirst().orElseThrow();
        assertEquals(2, first.getQuantity());
        assertEquals(20_000, first.getPrice());
        assertEquals("test1", first.getName());
    }
}