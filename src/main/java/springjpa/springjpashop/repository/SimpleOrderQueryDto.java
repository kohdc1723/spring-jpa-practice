package springjpa.springjpashop.repository;

import lombok.Data;
import springjpa.springjpashop.domain.Address;
import springjpa.springjpashop.domain.Order;
import springjpa.springjpashop.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryDto {
    private Long orderId;
    private String memberName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderQueryDto(
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
}
