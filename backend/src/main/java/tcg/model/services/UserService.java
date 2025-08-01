package tcg.model.services;

import tcg.model.entities.User;
import tcg.model.exceptions.AlreadyExistsException;
import tcg.model.exceptions.UserNotFoundException;

public interface UserService {

    void register(User user) throws AlreadyExistsException;

    User login (String userName, String password) throws UserNotFoundException; 
    
    //
}