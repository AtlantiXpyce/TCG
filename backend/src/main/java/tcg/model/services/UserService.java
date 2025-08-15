package tcg.model.services;

import tcg.model.entities.User;
import tcg.model.exceptions.AlreadyExistsException;
import tcg.model.exceptions.IncorrectParamsException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.UserNotFoundException;

public interface UserService {

    void register(User user) throws AlreadyExistsException;

    User login (String userName, String password) throws UserNotFoundException; 
    
    User getUser (Long id) throws UserNotFoundException;

    void userBecomesStore (Long id, Double latitude, Double longitude) throws IncorrectParamsException, NotTheUserException, UserNotFoundException;
}