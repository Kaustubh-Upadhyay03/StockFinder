package Backend.StockFinderBackend.services.impl;

import Backend.StockFinderBackend.model.Preferences;
import Backend.StockFinderBackend.model.StockTable;
import Backend.StockFinderBackend.model.User;
import Backend.StockFinderBackend.repository.PreferencesRepository;
import Backend.StockFinderBackend.repository.UserRepository;
import Backend.StockFinderBackend.services.UserService;
import Backend.StockFinderBackend.services.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockTableServiceImpl stockTableService;

    @Autowired
    private PreferencesRepository preferencesRepository;

    public User processUserLogin(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findBySubID(userDTO.getSub());

        User user = userOptional.orElseGet(() -> createUserFromDTO(userDTO));

        if (userOptional.isPresent()) {
            updateUserFromDTO(user, userDTO);
        }

        return userRepository.save(user);
    }

    private User createUserFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setSubID(userDTO.getSub());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getName());
        return user;
    }

    private void updateUserFromDTO(User user, UserDTO userDTO) {
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getName());
    }

    public Preferences getUserPreferences(String subId) {
        User user = userRepository.findBySubID(subId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        return preferencesRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Preferences not found"));
    }
}
