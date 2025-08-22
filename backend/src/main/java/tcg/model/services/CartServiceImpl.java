package tcg.model.services;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.CardStock;
import tcg.model.entities.CardStockDao;
import tcg.model.entities.Cart;
import tcg.model.entities.CartDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.CardStockNotFoundException;
import tcg.model.exceptions.QuantityExceededException;
import tcg.model.exceptions.UserNotFoundException;

@Service
@Transactional
public class CartServiceImpl implements CartService{

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CardStockDao cardStockDao;
    
    @Autowired
    private UserDao userDao;

    @Override
    public Cart addToCart(Long cardStockId, int quantity, Long userId) throws CardStockNotFoundException, UserNotFoundException, QuantityExceededException, InvalidParameterException{
        Optional<CardStock> stockAux = cardStockDao.findById(cardStockId);
        Optional<User> userAux = userDao.findById(userId);
        
        if(quantity < 1){
            throw new InvalidParameterException();
        }

        if(stockAux.isEmpty()){
            throw new CardStockNotFoundException();
        }
        CardStock stock = stockAux.get();
        
        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }

        if (stock.getQuantity() < quantity) {
            throw new QuantityExceededException();            
        }

        Cart cart = new Cart(stock, quantity, userAux.get());

        stock.setQuantity(stock.getQuantity()-quantity);
        stock.setCartQuantity(stock.getCartQuantity()+quantity);
        cardStockDao.save(stock);
        
        return cartDao.save(cart);
    }

    @Override
    public List<Cart> getCart(Long userId) throws UserNotFoundException {
        Optional<User> userAux = userDao.findById(userId);

        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }

        return cartDao.findByUserId(userId);
    }

}