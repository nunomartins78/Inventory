package inventory.controller;

import inventory.controller.dto.CreateMovementRequestDto;
import inventory.domain.StockMovement;
import inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    private final InventoryService inventoryService;

    public MovementController (InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockMovement createMovement (@Valid @RequestBody CreateMovementRequestDto request){
        return inventoryService.recordMovement(request.itemId(), request.type(), request.quantity());
    }
}
