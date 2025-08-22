package tcg.model.services;

import java.security.InvalidParameterException;
import java.util.List;

import tcg.model.entities.Cart;
import tcg.model.exceptions.CardStockNotFoundException;
import tcg.model.exceptions.CartNotFoundException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.QuantityExceededException;
import tcg.model.exceptions.UserNotFoundException;

public interface CartService {
    public Cart addToCart(Long cardStockId, int quantity, Long userId) throws CardStockNotFoundException, UserNotFoundException, QuantityExceededException, InvalidParameterException;
    
    public List<Cart> getCart(Long userId) throws UserNotFoundException;

    public void deleteFromCart(Long cartId, Long userId) throws CartNotFoundException, UserNotFoundException, NotTheUserException;

    //buyCart Long userId
}
