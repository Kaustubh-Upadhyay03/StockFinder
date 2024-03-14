package Backend.StockFinderBackend.services;

import Backend.StockFinderBackend.model.StockCandle;
import Backend.StockFinderBackend.services.dto.StockCandleDTO;

import java.util.List;

public interface StockCandleService {
    boolean tickerExists(String ticker);
    void replaceStockCandles(String ticker, StockCandleDTO newCandles);
    void saveStockCandles(StockCandleDTO stockCandleDTO);
    List<StockCandle> getStockCandlesForGraph(String ticker);
}
