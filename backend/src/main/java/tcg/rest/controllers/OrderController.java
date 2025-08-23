package tcg.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import tcg.model.exceptions.UserNotFoundException;
import tcg.model.services.OrderService;
import tcg.rest.common.JwtGenerator;
import tcg.rest.dtos.OrderTableConversor;
import tcg.rest.dtos.OrderTableDto;

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

    @GetMapping("")
    public List<OrderTableDto> getOrdersForBuyer(HttpServletRequest request) throws UserNotFoundException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        return OrderTableConversor.toOrderTableDtos(orderService.getOrdersForBuyer(authId));
    }
}
