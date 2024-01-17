package springjpa.springjpashop.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springjpa.springjpashop.domain.*;
import springjpa.springjpashop.repository.OrderRepository;
import springjpa.springjpashop.repository.SimpleOrderQueryDto;
import springjpa.springjpashop.repository.order.SimpleOrderQueryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SimpleOrderApiController {
    private final OrderRepository orderRepository;
    private final SimpleOrderQueryRepository simpleOrderQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> getOrdersV1() {
        List<Order> orders = orderRepository.findAllByJpql(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }

        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> getOrdersV2() {
        List<Order> orders = orderRepository.findAllByJpql(new OrderSearch());
        List<SimpleOrderDto> collect = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> getOrdersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> collect = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDto> getOrdersV4() {
        return simpleOrderQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String memberName;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            memberName = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
