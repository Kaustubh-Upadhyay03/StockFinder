package Backend.StockFinderBackend.repository;

import Backend.StockFinderBackend.model.Preferences;
import Backend.StockFinderBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, UUID> {

    Optional<Preferences> findByUser(User user);

    List<Preferences> findByUserId(UUID userID);
}
