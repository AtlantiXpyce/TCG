package tcg.model.services;

import java.util.List;

import tcg.model.entities.OrderLine;
import tcg.model.entities.OrderTable;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.OrderNotFoundException;
import tcg.model.exceptions.UserNotFoundException;

public interface OrderService {
    public List<OrderTable> getOrdersForBuyer(Long buyerId) throws UserNotFoundException;
    
    public List<OrderLine> getOrder(Long orderTableId, Long buyerId) throws UserNotFoundException, NotTheUserException, OrderNotFoundException;
    
    //public List<OrderLine> getOrderForSeller(Long sellerId);
}
