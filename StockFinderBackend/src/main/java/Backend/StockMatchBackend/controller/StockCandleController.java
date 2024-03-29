package Backend.StockFinderBackend.controller;

import Backend.StockFinderBackend.model.StockCandle;
import Backend.StockFinderBackend.repository.StockCandleRepository;
import Backend.StockFinderBackend.services.StockCandleService;
import Backend.StockFinderBackend.services.dto.StockCandleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/stockCandles")
public class StockCandleController {

    @Autowired
    private StockCandleRepository stockCandleRepository;


    private final StockCandleService stockCandleService;

    @Autowired
    public StockCandleController(StockCandleService stockCandleService) {
        this.stockCandleService = stockCandleService;
    }


    @GetMapping("/{id}")
    public StockCandle getStockCandleById(@PathVariable UUID id) {
        return stockCandleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock Candle not found"));
    }

    @PostMapping
    public StockCandle createStockCandle(@RequestBody StockCandle stockCandle) {
        return stockCandleRepository.save(stockCandle);
    }


    //sample (using for testing the string received for json data)
//    @PostMapping("/receiveStockQuote")
//    public ResponseEntity<String> receiveStockQuote(@RequestBody String stockCandle) {
//        // Print the received JSON
//        System.out.println("Received stock quote: " + stockCandle);
//
//        // Return a response
//        return ResponseEntity.ok("Received stock quote: " + stockCandle);
//    }

//    @PostMapping("/receiveStockQuote")
//    public ResponseEntity<?> saveStockCandles(@RequestBody StockCandleDTO stockCandleDTO) {
//        stockCandleService.saveStockCandles(stockCandleDTO);
//        return ResponseEntity.ok().build();
//    }
    @PostMapping("/receiveStockCandles")
    public ResponseEntity<?> saveStockCandles(@RequestBody StockCandleDTO stockCandleDTO) {
        // Check if the ticker already exists and replace the existing candles
        // we delete this ticker and replace the ticker data accordingly

        if (stockCandleService.tickerExists(stockCandleDTO.getTicker())) {
            replaceStockCandles(stockCandleDTO.getTicker(), stockCandleDTO);
        } else {
            stockCandleService.saveStockCandles(stockCandleDTO);
        }
        return ResponseEntity.ok("Stock candles processed successfully");
    }

    @GetMapping("/getStockCandles/{ticker}")
    public ResponseEntity<?> getStockCandles(@PathVariable String ticker) {
        List<StockCandle> candles = stockCandleService.getStockCandlesForGraph(ticker);
        return ResponseEntity.ok(candles);
    }

    public ResponseEntity<?> replaceStockCandles(@PathVariable String ticker, @RequestBody StockCandleDTO newCandles) {
        // Use the service to replace existing candles with new ones
        stockCandleService.replaceStockCandles(ticker, newCandles);
        return ResponseEntity.ok("Stock candles replaced successfully");
    }
}
