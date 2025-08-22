package com.example.campusevents.User.Controller;

import com.example.campusevents.User.DTO.*;
import com.example.campusevents.User.DTO.UserRegistrationDTO;
import com.example.campusevents.User.DTO.UserResponseDTO;
import com.example.campusevents.User.Model.User;
import com.example.campusevents.User.Service.UserService;
import com.example.campusevents.User.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserProfile(Principal principal) {
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        return user.map(value -> ResponseEntity.ok(toResponseDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(value -> ResponseEntity.ok(toResponseDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            User registeredUser = userService.registerUser(registrationDTO);
            return ResponseEntity.ok(toResponseDto(registeredUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UserUpdateDTO updatedUserDto, Principal principal) {
        String currentUsername = principal.getName();
        User user = userService.findByUsername(currentUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Check if the new email is already used by someone else
        userService.validateEmailUniqueness(updatedUserDto.getEmail(), user.getId());

        if (updatedUserDto.getFirstName() != null) {
            user.setFirstName(updatedUserDto.getFirstName());
        }
        if (updatedUserDto.getLastName() != null) {
            user.setLastName(updatedUserDto.getLastName());
        }
        if (updatedUserDto.getEmail() != null) {
            user.setEmail(updatedUserDto.getEmail());
        }
        if (updatedUserDto.getPassword() != null) {
            String hashedPassword = bCryptPasswordEncoder.encode(updatedUserDto.getPassword());
            user.setPassword(hashedPassword);
        }
        if (updatedUserDto.getYear() != null) {
            user.setYear(updatedUserDto.getYear());
        }

        userService.save(user);

        return ResponseEntity.ok(toResponseDto(user));
    }






    // DTO Conversion Helper
    private UserResponseDTO toResponseDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),

                user.getEmail(),
                user.getRole().name(),
                user.isVerified(),
                user.getYear()
        );
    }

    @PutMapping("/verify")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> verifyUser(@RequestParam String usernameToVerify) {
        try {
            userService.verifyUser(usernameToVerify);
            return ResponseEntity.ok("User " + usernameToVerify + " verified successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }




}
