package tcg.model.entities;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface UserDao extends CrudRepository<User, Long>, ListPagingAndSortingRepository<User, Long>{

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsernameIgnoreCase(String userName);

    Page<User> findByRole(String role, Pageable page);
}
