package tcg.model.entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrderLineDao extends CrudRepository<OrderLine, Long>{
    
    List<OrderLine> findByOrderId(Long orderId);

}
