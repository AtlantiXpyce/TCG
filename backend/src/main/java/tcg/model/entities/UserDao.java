package tcg.model.entities;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long>{

    boolean existsByUsername(String username);

    Optional<User> findByUsernameIgnoreCase(String userName);
}
