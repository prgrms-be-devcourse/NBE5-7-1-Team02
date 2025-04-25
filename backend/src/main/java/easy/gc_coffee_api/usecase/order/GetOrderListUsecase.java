package easy.gc_coffee_api.usecase.order;


import easy.gc_coffee_api.dto.OrderListResponseDto;
import easy.gc_coffee_api.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GetOrderListUsecase {

    private final OrderRepository repository;

    public List<OrderListResponseDto> execute(){
    }

}
