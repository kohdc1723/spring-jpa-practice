package springjpa.springjpashop.repository.order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpa.springjpashop.repository.SimpleOrderQueryDto;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SimpleOrderQueryRepository {
    private final EntityManager em;

    public List<SimpleOrderQueryDto> findOrderDtos() {
        return em.createQuery(
                        "select new springjpa.springjpashop.repository.SimpleOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                                " join o.member m" +
                                " join o.delivery d", SimpleOrderQueryDto.class)
                .getResultList();
    }
}
