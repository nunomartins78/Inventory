package inventory.service;

import inventory.domain.Item;
import inventory.domain.MovementType;
import inventory.domain.StockMovement;
import inventory.domain.exception.BusinessRuleException;
import inventory.domain.exception.NotFoundException;
import inventory.repository.ItemRepository;
import inventory.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final ItemRepository itemRepository;
    private final StockMovementRepository stockMovementRepository;

    public InventoryService (ItemRepository itemRepository, StockMovementRepository stockMovementRepository){
        this.itemRepository = itemRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public List <Item> getAllItems (){
        return itemRepository.findAll();
    }

    public Item getItemById(Long itemId) {
        Optional<Item> opt = itemRepository.findById(itemId);

        if (opt.isEmpty()) {
            throw new NotFoundException("Item não encontrado: id = " + itemId);
        }

        return opt.get();
    }

    public Item createItem (Item item){
        return itemRepository.save(item);
    }

    public Item updateItem (Long itemId, Item updatedItem){
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(()-> new NotFoundException("Item não encontrado: id = " + itemId));

        existingItem.setName(updatedItem.getName());
        existingItem.setMinStock(updatedItem.getMinStock());

        return itemRepository.save(existingItem);
    }

    public void deleteItem (Long itemId){
        boolean exists = itemRepository.existsById(itemId);
        if (!exists) {
            throw new NotFoundException("Item não encontrado: id = " + itemId);
        }
        itemRepository.deleteById(itemId);
    }

    public StockMovement recordMovement(Long itemId, MovementType type, int quantity){

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item não encontrado: id = " + itemId));

        if (type == MovementType.IN || type == MovementType.OUT) {
            if (quantity <= 0) {
                throw new BusinessRuleException("Quantidade deve ser maior que 0 para IN ou OUT.");
            }
        }

        if (type == MovementType.ADJUST) {
            if (quantity == 0) {
                throw new BusinessRuleException("ADJUST não pode ser 0.");
            }
        }

        int currentStock = calculateCurrentStock(itemId);

        int newStock = currentStock;

        if (type == MovementType.IN) {
            newStock = currentStock + quantity;
        }
        else if (type == MovementType.OUT) {
            newStock = currentStock - quantity;
        }
        else if (type == MovementType.ADJUST) {
            newStock = currentStock + quantity; // pode ser negativo (perda)
        }

        if (newStock < 0) {
            throw new BusinessRuleException("Operação inválida: stock não pode ser negativo.");
        }

        StockMovement movement = new StockMovement(item, type, quantity, Instant.now());
        return stockMovementRepository.save(movement);
    }


    public int calculateCurrentStock (Long itemId){
        List<StockMovement> movements = stockMovementRepository.findAll();

        int currentStock = 0;

        for (StockMovement movement : movements){
            Long movementItemId = movement.getItem().getId();
            if (!movementItemId.equals(itemId)){
                continue;
            }

            if (movement.getType() == MovementType.IN){
                currentStock = currentStock + movement.getQuantity();
            } else if (movement.getType() == MovementType.OUT) {
                currentStock = currentStock - movement.getQuantity();

            } else if (movement.getType() == MovementType.ADJUST) {
                currentStock = currentStock + movement.getQuantity();
            }
        }
        return currentStock;
    }
}
