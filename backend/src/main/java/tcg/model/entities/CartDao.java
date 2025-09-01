package tcg.model.entities;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CartDao extends CrudRepository<Cart, Long>{

    List<Cart> findByUserId(Long userId);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.cardStock.id = :cardStockId")
    Optional<Cart> findByUserIdAndCartStockId(@Param("userId") Long userId, @Param("cardStockId") Long cardStockId);
}
