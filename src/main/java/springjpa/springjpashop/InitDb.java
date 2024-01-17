package springjpa.springjpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springjpa.springjpashop.domain.*;
import springjpa.springjpashop.domain.item.Book;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = new Member();
            member.setName("member1");
            member.setAddress(new Address("Main", "Vancouver", "VVV123"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("Spring1");
            book1.setPrice(20000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("JPA1");
            book2.setPrice(30000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 30000, 3);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = new Member();
            member.setName("member2");
            member.setAddress(new Address("Willingdon", "Burnaby", "BBB123"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("Spring2");
            book1.setPrice(25000);
            book1.setStockQuantity(200);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("JPA2");
            book2.setPrice(35000);
            book2.setStockQuantity(300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 25000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 35000, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
