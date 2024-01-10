package springjpa.springjpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpa.springjpashop.domain.item.Book;
import springjpa.springjpashop.domain.item.Item;
import springjpa.springjpashop.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(itemId);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);

        return item;
    }

    @Transactional(readOnly = true)
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
