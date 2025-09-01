package tcg.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import tcg.model.entities.User;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.exceptions.CardStockNotFoundException;
import tcg.model.exceptions.NotAnAdminException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.UserNotFoundException;
import tcg.model.services.CardService;
import tcg.model.services.CardStockService;
import tcg.model.services.UserService;
import tcg.rest.common.JwtGenerator;
import tcg.rest.dtos.UserResponse;
import tcg.rest.dtos.UserResponseDtoConversor;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardStockService cardStockService;

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<String> handleCardNotFound(CardNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NotTheUserException.class)
    public ResponseEntity<String> handleNotTheUserException(NotTheUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(CardStockNotFoundException.class)
    public ResponseEntity<String> handleCardStockNotFoundException(CardStockNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(NotAnAdminException.class)
    public ResponseEntity<String> handleNotAnAdminException(NotAnAdminException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @PostMapping("/deleteCard")
    public void deleteCard(@RequestParam(required = false) Long cardId, HttpServletRequest request) throws UserNotFoundException, NotAnAdminException, CardNotFoundException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        cardService.deleteCard(cardId, authId);
    }

    @PostMapping("/deleteStock")
    public void deleteStock(@RequestParam(required = false) Long stockId, HttpServletRequest request) throws UserNotFoundException, CardStockNotFoundException, NotTheUserException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        cardStockService.deleteStock(stockId, authId);
    }

    @PostMapping("/deleteUser")
    public void deleteUser(@RequestParam(required = false) Long userId, HttpServletRequest request) throws UserNotFoundException, NotAnAdminException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        userService.deleteUser(authId, userId);
    }

    @GetMapping("/getUsers")
    public Page<UserResponse> getUsers(@RequestParam(required = false) Integer pageNum, HttpServletRequest request) throws NotAnAdminException, UserNotFoundException{
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);
        Pageable page = createPageable(pageNum);

        Page<User> userPage = userService.getUsers(authId, page);

        return UserResponseDtoConversor.toUserResponses(userPage);
    }
    
    public Pageable createPageable(Integer page) {
        if(page == null || page < 0){
            page = 0;
        }
        return PageRequest.of(page, 10);
    }
}
