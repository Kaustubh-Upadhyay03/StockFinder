package Backend.StockFinderBackend.services;

import Backend.StockFinderBackend.services.dto.PreferencesDTO;

public interface PreferencesService {
    void saveUserPreferences(PreferencesDTO preferencesDTO);
}
