package tcg.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tcg.model.entities.User;
import tcg.model.exceptions.AlreadyExistsException;
import tcg.model.exceptions.UserNotFoundException;
import tcg.model.services.UserService;
import tcg.rest.common.JwtGenerator;
import tcg.rest.dtos.LoginRequest;
import tcg.rest.dtos.LoginResponse;
import tcg.rest.dtos.RegisterRequest;
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
    public ResponseEntity<String> handleAlreadyExistsException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
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
        String token = jwtGenerator.generateToken(user.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
