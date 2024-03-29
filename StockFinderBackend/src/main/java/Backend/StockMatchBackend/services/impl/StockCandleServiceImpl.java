package Backend.StockFinderBackend.services.impl;

import Backend.StockFinderBackend.model.StockCandle;
import Backend.StockFinderBackend.repository.StockCandleRepository;
import Backend.StockFinderBackend.services.StockCandleService;
import Backend.StockFinderBackend.services.dto.StockCandleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StockCandleServiceImpl implements StockCandleService {

    private static final java.util.UUID UUID = java.util.UUID.randomUUID();
    @Autowired
    private StockCandleRepository stockCandleRepository;

    @Override
    public void saveStockCandles(StockCandleDTO stockCandleDTO) {
        List<StockCandle> candles = new ArrayList<>();
        for (int i = 0; i < stockCandleDTO.getT().size(); i++) {
            StockCandle candle = new StockCandle();
            candle.setId(UUID.randomUUID());
            candle.setTicker(stockCandleDTO.getTicker());
            candle.setUniqueCandleTimestamp(Instant.ofEpochSecond(stockCandleDTO.getT().get(i)));
            candle.setOpen(stockCandleDTO.getO().get(i));
            candle.setHigh(stockCandleDTO.getH().get(i));
            candle.setLow(stockCandleDTO.getL().get(i));
            candle.setClose(stockCandleDTO.getC().get(i));
            if (stockCandleDTO.getV() != null) {
                candle.setVolume(stockCandleDTO.getV().get(i));
            }
            candles.add(candle);
        }
        stockCandleRepository.saveAll(candles);
    }

    @Override
    public List<StockCandle> getStockCandlesForGraph(String ticker) {
        return stockCandleRepository.findByTicker(ticker);
    }
    @Override
    public boolean tickerExists(String ticker) {
        return stockCandleRepository.existsByTicker(ticker);
    }

    @Override
    @Transactional
    public void replaceStockCandles(String ticker,StockCandleDTO newCandles) {
        stockCandleRepository.deleteByTicker(ticker);
       saveStockCandles(newCandles);
    }




}