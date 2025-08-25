package tcg.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import tcg.model.entities.User;
import tcg.model.exceptions.AlreadyExistsException;
import tcg.model.exceptions.AlreadyStoreException;
import tcg.model.exceptions.IncorrectParamsException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.UserNotFoundException;
import tcg.model.services.UserService;
import tcg.rest.common.JwtGenerator;
import tcg.rest.dtos.LoginRequest;
import tcg.rest.dtos.LoginResponse;
import tcg.rest.dtos.RegisterRequest;
import tcg.rest.dtos.StoreParamsDto;
import tcg.rest.dtos.UserProfileDto;
import tcg.rest.dtos.UserResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtGenerator jwtGenerator;
    
    @Autowired
    private UserService userService;
    
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IncorrectParamsException.class)
    public ResponseEntity<String> handleIncorrectParamsException(IncorrectParamsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NotTheUserException.class)
    public ResponseEntity<String> handleNotTheUserException(NotTheUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(AlreadyStoreException.class)
    public ResponseEntity<String> handleAlreadyStoreException(AlreadyStoreException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) throws AlreadyExistsException {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setStore(false); 
        user.setRole("USER");
        userService.register(user);
        UserResponse response = new UserResponse(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws UserNotFoundException {
        User user = userService.login(request.getUsername(), request.getPassword());
        String token = jwtGenerator.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token, user.getUsername(), user.getRole(), user.getId(),user.isStore()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> profile(@PathVariable Long id) throws UserNotFoundException{
        User user = userService.getUser(id);
        if(user.isStore()){
            return ResponseEntity.ok(new UserProfileDto(user.getId(),user.getUsername(),user.getFirstName(),user.isStore(),user.getLocation().getLongitude(),user.getLocation().getLatitude()));
        }else
        return ResponseEntity.ok(new UserProfileDto(user.getId(),user.getUsername(),user.getFirstName(),user.isStore(),Double.valueOf(190),Double.valueOf(190)));
        
    }
    
    @PostMapping("/enableStore")
    public ResponseEntity<String> enableStore(@Valid @RequestBody StoreParamsDto storeParams, HttpServletRequest request) throws IncorrectParamsException, NotTheUserException, UserNotFoundException, AlreadyStoreException{
        
        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        if (!authId.equals(storeParams.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario no autorizado");
        }
        userService.userBecomesStore(storeParams.getId(), storeParams.getLatitude(), storeParams.getLongitude());
        return ResponseEntity.ok("Usuario es tienda");
    }

}
