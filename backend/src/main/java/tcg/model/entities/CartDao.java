package tcg.model.entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CartDao extends CrudRepository<Cart, Long>{
    List<Cart> findByUserId(Long userId);
}
