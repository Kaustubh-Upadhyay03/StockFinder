package Backend.StockFinderBackend.controller;

import Backend.StockFinderBackend.model.StockTable;
import Backend.StockFinderBackend.repository.StockTableRepository;
import Backend.StockFinderBackend.services.dto.StockTableDTO;
import Backend.StockFinderBackend.services.impl.StockTableServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/stocks")
public class StockTableController {

    @Autowired
    private StockTableRepository stockTableRepository;

    @Autowired
    private StockTableServiceImpl stockTableService;

    @GetMapping("/{id}")
    public StockTable getStockById(@PathVariable UUID id) {
        return stockTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    @PostMapping
    public StockTable createStock(@RequestBody StockTable stockTable) {
        return stockTableRepository.save(stockTable);
    }


    @PostMapping("/addStock")
    public ResponseEntity<StockTable> addStockInfo(@RequestBody StockTableDTO stockTableDTO) {
        StockTable stockTable = stockTableService.addStockInfo(stockTableDTO);
        return ResponseEntity.ok(stockTable);
    }

}
