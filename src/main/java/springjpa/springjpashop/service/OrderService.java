package springjpa.springjpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpa.springjpashop.domain.*;
import springjpa.springjpashop.domain.item.Item;
import springjpa.springjpashop.repository.ItemRepository;
import springjpa.springjpashop.repository.MemberRepository;
import springjpa.springjpashop.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancelOrder();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByJpql(orderSearch);
    }
}
