package tcg.model.services;

import java.security.InvalidParameterException;

import tcg.model.entities.Cart;
import tcg.model.exceptions.CardStockNotFoundException;
import tcg.model.exceptions.QuantityExceededException;
import tcg.model.exceptions.UserNotFoundException;

public interface CartService {
    public Cart addToCart(Long cardStockId, int quantity, Long userId) throws CardStockNotFoundException, UserNotFoundException, QuantityExceededException, InvalidParameterException;
    
    //getCart
    //deleteFromCart
}
