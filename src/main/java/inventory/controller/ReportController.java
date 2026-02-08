package inventory.controller;

import inventory.controller.dto.MovementRowDto;
import inventory.controller.dto.StockReportRowDto;
import inventory.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController (ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/stock")
    public List <StockReportRowDto> currentStock(){
        return reportService.getCurrentStockPerItem();
    }

    @GetMapping(value = "/stock.csv", produces = "text/csv")
    public ResponseEntity <byte[]> exportStockCsv (){
        List<StockReportRowDto> reportRows = reportService.getCurrentStockPerItem();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("itemId, itemName, currentStock, minStock\n");

        for (StockReportRowDto row : reportRows){
            csvBuilder.append(row.itemId()).append(",");
            csvBuilder.append(escapeCsv(row.itemName())).append(",");
            csvBuilder.append(row.currentStock()).append(",");
            csvBuilder.append(row.minStock()).append(",");
        }

        byte [] body = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "Attachment, filename:\"stock-report.csv\"")
                .contentType(new MediaType("text", "csv", StandardCharsets.UTF_8))
                .body(body);
    }

    @GetMapping(value = "/movements.csv", produces = "text/csv")
    public ResponseEntity <byte[]> exportMovementsCsv(){
        List <MovementRowDto> movementRows = reportService.getAllMovementsForExport();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("movementId,itemId,type,quantity,createdAt\n");

        for (MovementRowDto movementRow : movementRows){
            csvBuilder.append(movementRow.movementId()).append(",");
            csvBuilder.append(movementRow.itemId()).append(",");
            csvBuilder.append(movementRow.type()).append(",");
            csvBuilder.append(movementRow.quantity()).append(",");

            if (movementRow.createdAt() !=null){
                csvBuilder.append(movementRow.createdAt());
            }

            csvBuilder.append("\n");
        }

        byte [] body = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"movements.csv\"")
                .contentType(new MediaType("text", "csv", StandardCharsets.UTF_8))
                .body(body);
    }

    private String escapeCsv (String value){
        if (value == null){
            return "";
        }

        boolean mustQuote = value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r");

        if (!mustQuote) {
            return value;
        }

        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}
