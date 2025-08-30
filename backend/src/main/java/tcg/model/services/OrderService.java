package tcg.model.services;

import java.util.List;

import tcg.model.entities.OrderLine;
import tcg.model.entities.OrderTable;
import tcg.model.exceptions.AlreadyReviewedException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.OrderNotFoundException;
import tcg.model.exceptions.UserNotFoundException;

public interface OrderService {
    public List<OrderTable> getOrdersForBuyer(Long buyerId) throws UserNotFoundException;
    
    public List<OrderLine> getOrder(Long orderTableId, Long buyerId) throws UserNotFoundException, NotTheUserException, OrderNotFoundException;
    
    public void reviewOrder(Long orderTableId, Integer rating, String review, Long buyerId)
    throws UserNotFoundException, OrderNotFoundException, NotTheUserException, AlreadyReviewedException;

    public List<OrderTable> getOrdersForSeller(Long sellerId) throws UserNotFoundException;

    public void orderSent(Long sellerId, Long orderId) throws OrderNotFoundException, UserNotFoundException, NotTheUserException;

    public Double getAverageRatingForSeller(Long sellerId) throws UserNotFoundException;
}
