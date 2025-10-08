package com.store.tshirt_store;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; 
    }

    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        //  HASH THE PASSWORD BEFORE SAVING!
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
        Optional<User> optionalUser = userRepository.findByUsername(loginDetails.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            //  SECURELY COMPARE THE HASHED PASSWORD
            if (passwordEncoder.matches(loginDetails.getPassword(), user.getPassword())) {
                // Passwords match
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }

        // If user not found or password incorrect
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
}
