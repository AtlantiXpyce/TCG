package tcg.model.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.OrderLine;
import tcg.model.entities.OrderLineDao;
import tcg.model.entities.OrderTable;
import tcg.model.entities.OrderTableDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.AlreadyReviewedException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.OrderNotFoundException;
import tcg.model.exceptions.UserNotFoundException;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderTableDao orderTableDao;

    @Autowired
    private OrderLineDao orderLineDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<OrderTable> getOrdersForBuyer(Long buyerId) throws UserNotFoundException{
        Optional<User> userAux = userDao.findById(buyerId);

        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }
        
        return orderTableDao.findByBuyerIdOrderByIdDesc(buyerId);
    }

    @Override
    public List<OrderLine> getOrder(Long orderTableId, Long userId) throws UserNotFoundException, NotTheUserException, OrderNotFoundException{
        Optional<User> userAux = userDao.findById(userId);
        Optional<OrderTable> orderTableAux = orderTableDao.findById(orderTableId);
        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }
        if(orderTableAux.isEmpty()){
            throw new OrderNotFoundException();
        }
        if(!((userId == orderTableAux.get().getBuyer().getId()) || (userId == orderTableAux.get().getSeller().getId()))){
            throw new NotTheUserException();
        }
        return orderLineDao.findByOrderId(orderTableId);
    }

    @Override
    public void reviewOrder(Long orderTableId, Integer rating, String review, Long buyerId) throws UserNotFoundException, OrderNotFoundException, NotTheUserException, AlreadyReviewedException{
        Optional<User> userAux = userDao.findById(buyerId);
        Optional<OrderTable> orderTableAux = orderTableDao.findById(orderTableId);
        OrderTable orderTable;
        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }
        if(orderTableAux.isEmpty()){
            throw new OrderNotFoundException();
        }
        if(!(buyerId == orderTableAux.get().getBuyer().getId())){
            throw new NotTheUserException();
        }
        
        orderTable = orderTableAux.get();

        if (orderTable.getReview() != null) {
            throw new AlreadyReviewedException();
        }
        
        orderTable.setArrivalDate(LocalDateTime.now());
        orderTable.setRating(rating);
        orderTable.setReview(review);

        orderTableDao.save(orderTable);
    }

    @Override
    public List<OrderTable> getOrdersForSeller(Long sellerId) throws UserNotFoundException{
        Optional<User> userAux = userDao.findById(sellerId);

        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }
        
        return orderTableDao.findBySellerIdOrderByIdDesc(sellerId);
    }

    @Override
    public void orderSent(Long sellerId, Long orderId) throws OrderNotFoundException, UserNotFoundException, NotTheUserException {
        Optional<User> userAux = userDao.findById(sellerId);
        Optional<OrderTable> orderTableAux = orderTableDao.findById(orderId);
        OrderTable orderTable;

        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }
        if(orderTableAux.isEmpty()){
            throw new OrderNotFoundException();
        }
        if(!(sellerId == orderTableAux.get().getSeller().getId())){
            throw new NotTheUserException();
        }

        orderTable = orderTableAux.get();
        orderTable.setIsSent(true);

        orderTableDao.save(orderTable);
    }

    public Double getAverageRatingForSeller(Long sellerId) throws UserNotFoundException {
        if (userDao.findById(sellerId).isEmpty()) {
            throw new UserNotFoundException();
        }
        return orderTableDao.findAverageRatingForSeller(sellerId);
    }
    
    
}
