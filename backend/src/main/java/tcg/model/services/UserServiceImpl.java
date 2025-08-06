package tcg.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.AlreadyExistsException;
import tcg.model.exceptions.UserNotFoundException;


@Service
@Transactional
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) throws AlreadyExistsException{
        String username = user.getUsername();

        if(userDao.existsByUsername(username)){
            throw new AlreadyExistsException(username);
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

}
