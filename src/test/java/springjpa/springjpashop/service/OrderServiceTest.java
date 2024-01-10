package springjpa.springjpashop.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springjpa.springjpashop.domain.Address;
import springjpa.springjpashop.domain.Member;
import springjpa.springjpashop.domain.Order;
import springjpa.springjpashop.domain.OrderStatus;
import springjpa.springjpashop.domain.item.Book;
import springjpa.springjpashop.domain.item.Item;
import springjpa.springjpashop.exception.NotEnoughStockException;
import springjpa.springjpashop.repository.OrderRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void testOrder() {
        Member member = createMember("member1", new Address("Main Street", "Vancouver", "V1W2X3"));
        Book book = createBook("Harry Potter", 100, 10);

        int orderCount = 3;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        assertThat(order.getTotalPrice()).isEqualTo(book.getPrice() * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(7);
    }

    @Test
    void testCancel() {
        Member member = createMember("member1", new Address("Main Street", "Vancouver", "V1W2X3"));
        Book book = createBook("HP", 20000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    @Test
    void testQuantityExceed() {
        Member member = createMember("member1", new Address("Main Street", "Vancouver", "V1W2X3"));
        Item item = createBook("Harry Potter", 10000, 10);

        int orderCount = 11;

        Assertions.assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    private Book createBook(String name, int orderPrice, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(orderPrice);
        book.setStockQuantity(stockQuantity);
        em.persist(book);

        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);

        return member;
    }
}