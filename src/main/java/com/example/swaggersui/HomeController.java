package com.example.swaggersui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class HomeController {


    private final UserRepository userRepository;


    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public ResponseEntity<List<User>> getUser() {
        List<User> storedUser = userRepository.findAll();
        if (!storedUser.isEmpty()) {
            return ResponseEntity.ok(storedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto user) throws URISyntaxException {
        User saved = userRepository.save(new User(user.username(), user.phoneNumber()));
        System.out.println(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable UUID userId, @RequestBody User user) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User storedUser = userOptional.get();
            storedUser.setUsername(user.getUsername() != null ? user.getUsername() : storedUser.getUsername());
            storedUser.setPhoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : storedUser.getPhoneNumber());
            userRepository.save(storedUser);
            return ResponseEntity.ok("User updated: " + storedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}