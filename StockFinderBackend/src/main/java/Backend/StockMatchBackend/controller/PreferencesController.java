package Backend.StockFinderBackend.controller;

import Backend.StockFinderBackend.model.Preferences;
import Backend.StockFinderBackend.repository.PreferencesRepository;
import Backend.StockFinderBackend.services.PreferencesService;
import Backend.StockFinderBackend.services.dto.PreferencesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/preferences")
public class PreferencesController {

    @Autowired
    private PreferencesRepository preferencesRepository;
    @Autowired
    private PreferencesService preferencesService;

    @GetMapping("/{id}")
    public Preferences getPreferencesById(@PathVariable UUID id) {
        return preferencesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preferences not found"));
    }

    @PostMapping
    public Preferences createPreferences(@RequestBody Preferences preferences) {
        return preferencesRepository.save(preferences);
    }

    @PostMapping("/saveUserPreferences")
    public ResponseEntity<?> saveUserPreferences(@RequestBody PreferencesDTO preferencesDTO) {
        preferencesService.saveUserPreferences(preferencesDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Preferences saved successfully");
        return ResponseEntity.ok(response); // Returns a JSON body with the message
    }

}
