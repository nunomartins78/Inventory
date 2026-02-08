package inventory.controller.dto;

public record StockReportRowDto (
        Long itemId,
        String itemName,
        int currentStock,
        int minStock
) {}
