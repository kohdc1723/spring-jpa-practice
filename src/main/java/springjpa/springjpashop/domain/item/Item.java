package springjpa.springjpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import springjpa.springjpashop.domain.Category;
import springjpa.springjpashop.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void increaseStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void decreaseStock(int quantity) {
        int rest = this.stockQuantity - quantity;

        if (rest < 0) {
            throw new NotEnoughStockException("Not enough stock.");
        }

        this.stockQuantity = rest;
    }
}
