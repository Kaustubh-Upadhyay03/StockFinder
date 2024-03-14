package Backend.StockFinderBackend.services;

import Backend.StockFinderBackend.model.User;
import Backend.StockFinderBackend.services.dto.UserDTO;

public interface UserService {
    User processUserLogin(UserDTO userDTO);
}
