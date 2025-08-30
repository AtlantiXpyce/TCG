package tcg.model.entities;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderTableDao extends CrudRepository<OrderTable, Long>{
    List<OrderTable> findByBuyerIdOrderByIdDesc(Long buyerId);
    
    List<OrderTable> findBySellerIdOrderByIdDesc(Long buyerId);

    @Query("SELECT AVG(o.rating) " +
        "FROM OrderTable o " +
        "WHERE o.seller.id = :sellerId " +
        "AND o.rating IS NOT NULL " +
        "AND o.review IS NOT NULL")
    Double findAverageRatingForSeller(@Param("sellerId") Long sellerId);
}
