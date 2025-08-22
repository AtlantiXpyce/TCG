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
import tcg.model.exceptions.CartNotFoundException;
import tcg.model.exceptions.NotTheUserException;
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
        Cart cart;
        Optional<CardStock> stockAux = cardStockDao.findById(cardStockId);
        Optional<User> userAux = userDao.findById(userId);
        Optional<Cart> cartAux = cartDao.findByUserIdAndCartStockId(userId, cardStockId);
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

        if(!cartAux.isEmpty()){
            cart = cartAux.get();
            cart.setQuantity(cart.getQuantity() + quantity);
        }else{
            cart = new Cart(stock, quantity, userAux.get());
        }

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

    @Override
    public void deleteFromCart(Long cartId, Long userId) throws CartNotFoundException, UserNotFoundException, NotTheUserException {
        Cart cart;
        CardStock cardStock;
        Optional<User> userAux = userDao.findById(userId);
        Optional<Cart> cartAux = cartDao.findById(cartId);
        int quantityAux;
        int cartQuantityAux;
        
        if(userAux.isEmpty()){
            throw new UserNotFoundException();
        }
        if(cartAux.isEmpty()){
            throw new CartNotFoundException();
        }
        if (cartAux.get().getUser() != userAux.get()) {
            throw new NotTheUserException();
        }
        
        cart = cartAux.get();
        cardStock = cart.getCardStock();

        quantityAux = cart.getQuantity();
        
        cardStock.setQuantity(cardStock.getQuantity() + quantityAux);
        cardStock.setCartQuantity(cardStock.getCartQuantity() - quantityAux);
        
        cartDao.delete(cart);
    }

}