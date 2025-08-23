package tcg.model.services;

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
    public List<OrderLine> getOrder(Long orderTableId, Long buyerId) throws UserNotFoundException, NotTheUserException, OrderNotFoundException{
        Optional<User> userAux = userDao.findById(buyerId);
        Optional<OrderTable> orderTableAux = orderTableDao.findById(orderTableId);
        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }
        if(orderTableAux.isEmpty()){
            throw new OrderNotFoundException();
        }
        if(!(buyerId == orderTableAux.get().getBuyer().getId())){
            throw new NotTheUserException();
        }
        return orderLineDao.findByOrderId(orderTableId);
    }
    
}
