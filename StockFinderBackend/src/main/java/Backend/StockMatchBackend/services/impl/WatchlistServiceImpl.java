package Backend.StockFinderBackend.services.impl;

import Backend.StockFinderBackend.model.StockTable;
import Backend.StockFinderBackend.model.User;
import Backend.StockFinderBackend.model.Watchlist;
import Backend.StockFinderBackend.repository.StockTableRepository;
import Backend.StockFinderBackend.repository.UserRepository;
import Backend.StockFinderBackend.repository.WatchlistRepository;
import Backend.StockFinderBackend.services.WatchlistService;
import Backend.StockFinderBackend.services.dto.WatchListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockTableRepository stockTableRepository; // Assuming you have a repository for stocks
    @Autowired
    private WatchlistRepository watchlistRepository;

    //testing purposes only
//    @Override
//    public void addToWatchlist(WatchListDTO watchlistDTO) {
//        User user = userRepository.findBySubID(watchlistDTO.getSubID())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        StockTable stock = stockTableRepository.findBySymbol(watchlistDTO.getTicker())
//                .orElseThrow(() -> new RuntimeException("Stock not found"));
//
//        if ("like".equals(watchlistDTO.getLike())) {
//            Watchlist watchlist = new Watchlist();
//            watchlist.setUser(user);
//            watchlist.setStock(stock);
//            watchlistRepository.save(watchlist);
//        }
//
//    }

    @Override
    public void processWatchlistAction(WatchListDTO watchlistDTO) {
        switch (watchlistDTO.getAction().toLowerCase()) {
            case "like":
                addToWatchlist(watchlistDTO);
                break;
            case "remove":
                removeFromWatchlist(watchlistDTO);
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
    }
    @Override
    public void addToWatchlist(WatchListDTO watchlistDTO) {
        User user = userRepository.findBySubID(watchlistDTO.getSubID())
                .orElseThrow(() -> new RuntimeException("User not found"));

        StockTable stock = stockTableRepository.findBySymbol(watchlistDTO.getTicker())
                .orElseThrow(() -> new RuntimeException("Stock not found"));


        Optional<Watchlist> existingEntry = watchlistRepository.findByUserAndStock(user, stock);

        if (existingEntry.isPresent()) {

            return;
        }

        if ("like".equals(watchlistDTO.getAction())) {
            Watchlist watchlist = new Watchlist();
            watchlist.setUser(user);
            watchlist.setStock(stock);
            watchlistRepository.save(watchlist);
        }
    }

    @Override
    public void removeFromWatchlist(WatchListDTO watchlistDTO) {
        User user = userRepository.findBySubID(watchlistDTO.getSubID())
                .orElseThrow(() -> new RuntimeException("User not found"));

        StockTable stock = stockTableRepository.findBySymbol(watchlistDTO.getTicker())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        watchlistRepository.findByUserAndStock(user, stock)
                .ifPresent(watchlistRepository::delete);
    }

    @Override
    public List<StockTable> getWatchlistByUserSubId(String subID) {
        User user = userRepository.findBySubID(subID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Watchlist> watchlistEntries = watchlistRepository.findByUser(user);

        return watchlistEntries.stream()
                .map(Watchlist::getStock)
                .collect(Collectors.toList());
    }
}
