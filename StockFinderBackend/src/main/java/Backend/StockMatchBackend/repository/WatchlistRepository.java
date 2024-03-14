package Backend.StockFinderBackend.repository;


import Backend.StockFinderBackend.model.StockTable;
import Backend.StockFinderBackend.model.User;
import Backend.StockFinderBackend.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, UUID> {
    Optional<Watchlist> findByUserAndStock(User user, StockTable stock);
    List<Watchlist> findByUser(User user);
}
