package Backend.StockFinderBackend.services;

import Backend.StockFinderBackend.model.StockTable;
import Backend.StockFinderBackend.services.dto.WatchListDTO;

import java.util.List;

public interface WatchlistService {
    void addToWatchlist(WatchListDTO watchlistDTO);
    void removeFromWatchlist(WatchListDTO watchlistDTO);
    void processWatchlistAction(WatchListDTO watchlistDTO);
    List<StockTable> getWatchlistByUserSubId(String subId);
}
