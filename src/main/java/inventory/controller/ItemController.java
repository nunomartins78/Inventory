package inventory.controller;

import inventory.domain.Item;
import inventory.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final InventoryService inventoryService;

    public ItemController (InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List <Item> getAllItems(){
        return inventoryService.getAllItems();
    }

    @GetMapping ("/{itemId}")
    public Item getItemById (@PathVariable Long itemId) {
        return inventoryService.getItemById(itemId);
    }

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    public Item createItem (@RequestBody Item item){
        return inventoryService.createItem(item);
    }

    @PutMapping ("/{itemId}")
    public Item updateItem (@PathVariable Long itemId, @RequestBody Item updatedItem){
        return inventoryService.updateItem(itemId, updatedItem);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem (@PathVariable Long itemId){
        inventoryService.deleteItem(itemId);
    }
}
