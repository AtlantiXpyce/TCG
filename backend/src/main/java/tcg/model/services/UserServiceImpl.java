package tcg.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.Location;
import tcg.model.entities.LocationDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.AlreadyExistsException;
import tcg.model.exceptions.AlreadyStoreException;
import tcg.model.exceptions.IncorrectParamsException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.UserNotFoundException;


@Service
@Transactional
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) throws AlreadyExistsException{
        String username = user.getUsername();
        String email = user.getEmail();
        if(userDao.existsByUsername(username)){
            throw new AlreadyExistsException(username);
        }else if (userDao.existsByEmail(email)){
            throw new AlreadyExistsException(email);
        }else{
            user.setRole("USER");
            user.setStore(false);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(user);
        }
    }

    @Override
    public User login(String username, String password) throws UserNotFoundException{
        Optional<User> user = userDao.findByUsernameIgnoreCase(username);
        
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        if(!passwordEncoder.matches(password, user.get().getPassword())){
            throw new UserNotFoundException();
        }

        return user.get();
    }

    @Override
    public User getUser(Long id) throws UserNotFoundException{
        Optional<User> user = userDao.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return user.get();
    }

    @Override
    public void userBecomesStore(Long id, Double latitude, Double longitude) throws AlreadyStoreException, NotTheUserException, IncorrectParamsException, UserNotFoundException{
        Optional<User> userAux = userDao.findById(id);
        
        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }

        User user = userAux.get();
        if(user.isStore()){
            throw new AlreadyStoreException();
        }
        if (latitude == null || longitude == null ||
            latitude < -90 || latitude > 90 ||
            longitude < -180 || longitude > 180) {
                throw new IncorrectParamsException();
        }

        Location location = new Location();
        
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location = locationDao.save(location);

        user.setLocation(location);
        user.setStore(true);

        userDao.save(user);
    }
}
