package com.iraportal.accenture.Controller;

import com.iraportal.accenture.model.User;
import com.iraportal.accenture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UpdateController {

    private final UserRepository userRepository;

    @Autowired
    public UpdateController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {

        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            // Updatng the fields as per the object
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setName(updatedUser.getName());

            // Save the updated user
            userRepository.save(user);

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
