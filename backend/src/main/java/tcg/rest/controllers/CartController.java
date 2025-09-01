package tcg.rest.controllers;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import tcg.model.entities.Cart;
import tcg.model.entities.OrderLine;
import tcg.model.exceptions.CardStockNotFoundException;
import tcg.model.exceptions.CartNotFoundException;
import tcg.model.exceptions.EmptyCartException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.QuantityExceededException;
import tcg.model.exceptions.UserNotFoundException;
import tcg.model.services.CartService;
import tcg.rest.common.JwtGenerator;
import tcg.rest.dtos.CartDto;
import tcg.rest.dtos.CartLineDto;
import tcg.rest.dtos.CartLineDtoConversor;
import tcg.rest.dtos.OrderLineDto;
import tcg.rest.dtos.OrderLineDtoConversor;


@RestController
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private CartService cartService;

    @ExceptionHandler(CardStockNotFoundException.class)
    public ResponseEntity<String> handleCardStockNotFoundException(CardStockNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<String> handleCartNotFoundException(CartNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(QuantityExceededException.class)
    public ResponseEntity<String> handleQuantityExceededException(QuantityExceededException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<String> handleInvalidParameterException(InvalidParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<String> handleEmptyCartException(EmptyCartException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NotTheUserException.class)
    public ResponseEntity<String> handleNotTheUserException(NotTheUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @PostMapping("")
    public ResponseEntity<Long> addToCart(@RequestBody CartDto dto, HttpServletRequest request) throws CardStockNotFoundException, UserNotFoundException, QuantityExceededException{

        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        Cart cart = cartService.addToCart(dto.getCardStockId(), dto.getQuantity(), authId);

        return ResponseEntity.ok(cart.getId());
    }

    @GetMapping("")
    public List<CartLineDto> getCart(HttpServletRequest request) throws UserNotFoundException {
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);
        return CartLineDtoConversor.toCartLineDtos(cartService.getCart(authId));
    }

    @PostMapping("/delete")
    public void deleteFromCart(@RequestBody Long cartId, HttpServletRequest request) throws CartNotFoundException, UserNotFoundException, NotTheUserException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        cartService.deleteFromCart(cartId, authId);
    }
    
    @PostMapping("/buy")
    public List<OrderLineDto> buyCart(HttpServletRequest request) throws UserNotFoundException, EmptyCartException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);
        List<OrderLine> orderLines = cartService.buyCart(authId);

        return OrderLineDtoConversor.toOrderLineDtos(orderLines);
    }
}