package com.example.campusevents.User.Service;

import com.example.campusevents.User.DTO.UserRegistrationDTO;
import com.example.campusevents.User.Model.User;
import com.example.campusevents.User.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.campusevents.Security.Jwt.JwtUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service

public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;




    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;



    }
    public User registerUser(UserRegistrationDTO dto) {
        if (!dto.getEmail().endsWith("@purdue.edu")) {
            throw new IllegalArgumentException("Only purdue.edu emails allowed");
        }
        // Check if email already exists
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username is already registered");
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());


        user.setYear(dto.getYear());

        user.setVerified(false);


        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setRole(User.Role.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        return savedUser;
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Get all verified users
    public List<User> getVerifiedUsers() {
        return userRepository.findByVerifiedTrue();
    }

    public List<User> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role);
    }

    public void verifyUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.isVerified()) {
            user.setVerified(true);
            userRepository.save(user);
        }
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public void save(User user) {
        userRepository.save(user);
    }

    public void validateEmailUniqueness(String email, Long currentUserId) {
        Optional<User> userWithEmail = findByEmail(email);
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Email is already in use by another user.");
        }
    }
}
