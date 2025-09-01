package tcg.model.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tcg.model.entities.User;
import tcg.model.exceptions.AlreadyExistsException;
import tcg.model.exceptions.AlreadyStoreException;
import tcg.model.exceptions.IncorrectParamsException;
import tcg.model.exceptions.NotAnAdminException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.UserNotFoundException;

public interface UserService {

    void register(User user) throws AlreadyExistsException;

    User login (String userName, String password) throws UserNotFoundException; 
    
    User getUser (Long id) throws UserNotFoundException;

    void userBecomesStore (Long id, Double latitude, Double longitude) throws AlreadyStoreException, IncorrectParamsException, NotTheUserException, UserNotFoundException;

    void deleteUser(Long adminId, Long userId) throws UserNotFoundException, NotAnAdminException;

    Page<User> getUsers(Long adminId, Pageable page) throws NotAnAdminException, UserNotFoundException;
}