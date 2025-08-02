package tcg.model.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tcg.model.entities.User;
import tcg.model.exceptions.AlreadyExistsException;
import tcg.model.exceptions.UserNotFoundException;
import tcg.model.rest.dtos.LoginRequest;
import tcg.model.rest.dtos.RegisterRequest;
import tcg.model.rest.dtos.UserResponse;
import tcg.model.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
	private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) throws AlreadyExistsException {
        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRole("USER");
        user.setStore(false);

        userService.register(user);
        UserResponse response = new UserResponse(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) throws UserNotFoundException {
        try {
            User user = userService.login(request.getUsername(), request.getPassword());
            UserResponse response = new UserResponse(user);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(401).body(null);
        }
    }
    
}
