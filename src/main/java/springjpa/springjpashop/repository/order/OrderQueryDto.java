package springjpa.springjpashop.repository.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import springjpa.springjpashop.domain.Address;
import springjpa.springjpashop.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDto {
    private Long orderId;
    private String memberName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(
            Long orderId,
            String memberName,
            LocalDateTime orderDate,
            OrderStatus orderStatus,
            Address address
    ) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public OrderQueryDto(
            Long orderId,
            String memberName,
            LocalDateTime orderDate,
            OrderStatus orderStatus,
            Address address,
            List<OrderItemQueryDto> orderItems
    ) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }
}
