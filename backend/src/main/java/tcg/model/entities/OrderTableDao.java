package tcg.model.entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrderTableDao extends CrudRepository<OrderTable, Long>{
    List<OrderTable> findByBuyerIdOrderByIdDesc(Long buyerId);
    
    List<OrderTable> findBySellerIdOrderByIdDesc(Long buyerId);
}
