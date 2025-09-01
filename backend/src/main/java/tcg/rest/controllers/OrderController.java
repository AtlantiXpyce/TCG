package tcg.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import tcg.model.exceptions.AlreadyReviewedException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.OrderNotFoundException;
import tcg.model.exceptions.UserNotFoundException;
import tcg.model.services.OrderService;
import tcg.rest.common.JwtGenerator;
import tcg.rest.dtos.OrderLineDto;
import tcg.rest.dtos.OrderLineDtoConversor;
import tcg.rest.dtos.OrderTableConversor;
import tcg.rest.dtos.OrderTableDto;
import tcg.rest.dtos.ReviewDto;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private JwtGenerator jwtGenerator;
    
    @Autowired
    private OrderService orderService;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NotTheUserException.class)
    public ResponseEntity<String> handleNotTheUserException(NotTheUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AlreadyReviewedException.class)
    public ResponseEntity<String> handleAlreadyReviewedException(AlreadyReviewedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @GetMapping("")
    public List<OrderTableDto> getOrdersForBuyer(HttpServletRequest request) throws UserNotFoundException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        return OrderTableConversor.toOrderTableDtos(orderService.getOrdersForBuyer(authId), false);
    }

    @GetMapping("/seller")
    public List<OrderTableDto> getOrdersForSeller(HttpServletRequest request) throws UserNotFoundException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        return OrderTableConversor.toOrderTableDtos(orderService.getOrdersForSeller(authId), true);
    }

    @GetMapping("/lines")
    public List<OrderLineDto> getOrderLines(@RequestParam(required = true) Long orderId, HttpServletRequest request) throws UserNotFoundException, NotTheUserException, OrderNotFoundException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        return OrderLineDtoConversor.toOrderLineDtos(orderService.getOrder(orderId, authId));
    }

    @PostMapping("/review")
    public String reviewOrder(@Valid @RequestBody ReviewDto dto, HttpServletRequest request) throws UserNotFoundException, NotTheUserException, OrderNotFoundException, AlreadyReviewedException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);
        
        orderService.reviewOrder(dto.getOrderTableId(), dto.getRating(), dto.getReview(), dto.getBuyerId());

        return "Pedido confirmado y evaluado";
    }

    @PostMapping("/sent")
    public String confirmOrder(@RequestParam(required = true) Long orderId, HttpServletRequest request) throws UserNotFoundException, NotTheUserException, OrderNotFoundException, AlreadyReviewedException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        orderService.orderSent(authId, orderId);
        
        return "Pedido enviado";
    }
}
