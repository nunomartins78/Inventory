package inventory.service;

import inventory.controller.dto.MovementRowDto;
import inventory.controller.dto.StockReportRowDto;
import inventory.domain.Item;
import inventory.domain.MovementType;
import inventory.domain.StockMovement;
import inventory.repository.ItemRepository;
import inventory.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {

    private final ItemRepository itemRepository;
    private final StockMovementRepository stockMovementRepository;

    public ReportService (ItemRepository itemRepository, StockMovementRepository stockMovementRepository){
        this.itemRepository = itemRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public List<StockReportRowDto> getCurrentStockPerItem (){
        List<Item> items = itemRepository.findAll();
        List<StockMovement> movements = stockMovementRepository.findAll();

        Map<Long, Integer> stockByItemId = new HashMap<>();
        for (StockMovement movement : movements){
            Long itemId = movement.getItem().getId();

            int delta = movement.getQuantity();

            if (movement.getType() == MovementType.OUT){
                delta = -movement.getQuantity();
            }

            Integer currentValue = stockByItemId.get(itemId);
            if (currentValue == null){
                stockByItemId.put(itemId, delta);
            } else {
                stockByItemId.put(itemId, currentValue + delta);
            }
        }

        List<StockReportRowDto> reportRows = new ArrayList<>();

        for (Item item : items) {
            Integer stockValue = stockByItemId.get(item.getId());

            int currentStock = 0;
            if (stockValue != null){
                currentStock = stockValue.intValue();
            }

            int minStock =item.getMinStock();

            reportRows.add (new StockReportRowDto(
                    item.getId(),
                    item.getName(),
                    currentStock,
                    minStock
            ));
        }
        return reportRows;
    }

    public List <StockReportRowDto> getItemsBelowMinStock (){
        List <StockReportRowDto> all = getCurrentStockPerItem();
        List <StockReportRowDto> result = new ArrayList<>();

        for (StockReportRowDto row : all){
            if (row.currentStock() < row.minStock()){
                result.add(row);
            }
        }

        return result;
    }

    public List<MovementRowDto> getAllMovementsForExport() {
        List<StockMovement> movements = stockMovementRepository.findAll();
        List<MovementRowDto> result = new ArrayList<>();

        for (StockMovement movement : movements) {
            MovementRowDto row = new MovementRowDto(
                    movement.getId(),
                    movement.getItem().getId(),
                    movement.getType(),
                    movement.getQuantity(),
                    movement.getCreatedAt()
            );
            result.add(row);
        }

        return result;
    }
}
