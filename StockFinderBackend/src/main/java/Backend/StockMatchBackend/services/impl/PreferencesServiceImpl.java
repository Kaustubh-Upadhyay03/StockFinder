package Backend.StockFinderBackend.services.impl;

import Backend.StockFinderBackend.model.Preferences;
import Backend.StockFinderBackend.model.User;
import Backend.StockFinderBackend.repository.PreferencesRepository;
import Backend.StockFinderBackend.repository.UserRepository;
import Backend.StockFinderBackend.services.PreferencesService;
import Backend.StockFinderBackend.services.dto.PreferencesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PreferencesServiceImpl implements PreferencesService {
    private static final java.util.UUID UUID= java.util.UUID.randomUUID();
    @Autowired
    private PreferencesRepository preferencesRepository;
    @Autowired
    private UserRepository userRepository;

//    @Override
//    public void saveUserPreferences(PreferencesDTO preferencesDTO) {
//        Optional<User> userOptional = userRepository.findBySubID(preferencesDTO.getSubID());
//        if (!userOptional.isPresent()) {
//            throw new RuntimeException("User not found"); // Replace with appropriate exception handling
//        }
//        User user = userOptional.get();
//
//        // Check if preferences for this user already exist
//        Optional<Preferences> existingPreferences = preferencesRepository.findByUser(user);
//        Preferences preferences = existingPreferences.orElse(new Preferences());
//
//        // Set or update the preferences fields
//        preferences.setUser(user);
//        Double beta = preferencesDTO.getBeta();
//        preferences.setBeta(preferencesDTO.getBeta());
//        preferences.setAnalystScore(preferencesDTO.getAnalystScore());
//        preferences.setTimeInMarket(preferencesDTO.getTimeInMarket());
//        preferences.setMarketCapMillions(preferencesDTO.getMarketCapMillions());
//        preferences.setIndustry(preferencesDTO.getIndustry());
//        preferences.setRiskLevel((categorizeRiskLevel(beta)));
//        // Save or update the preferences in the database
//        preferencesRepository.save(preferences);
//    }
    @Override
    public void saveUserPreferences(PreferencesDTO preferencesDTO) {
        Optional<User> userOptional = userRepository.findBySubID(preferencesDTO.getSubID());
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Optional<Preferences> existingPreferences = preferencesRepository.findByUser(user);
        Preferences preferences = existingPreferences.orElse(new Preferences());

        preferences.setUser(user);
        preferences.setBeta(preferencesDTO.getBeta());
        preferences.setAnalystScore(preferencesDTO.getAnalystScore());
        preferences.setTimeInMarket(preferencesDTO.getTimeInMarket());
        preferences.setMarketCapMillions(preferencesDTO.getMarketCapMillions());
        preferences.setIndustryList(preferencesDTO.getIndustryList());
        preferences.setRiskLevel(categorizeRiskLevel(preferencesDTO.getBeta()));

        preferencesRepository.save(preferences);
    }


    private String categorizeRiskLevel(Double beta) {
        if (beta == null) {
            return "unknown";
        }
        if (beta < 1.0) {
            return "low";
        } else if (beta <= 1.5) {
            return "medium";
        } else {
            return "high";
        }
    }


}
