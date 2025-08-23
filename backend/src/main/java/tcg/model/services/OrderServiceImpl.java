package tcg.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.OrderLineDao;
import tcg.model.entities.OrderTable;
import tcg.model.entities.OrderTableDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
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
        
        return orderTableDao.findByBuyerId(buyerId);
    }
    
}
