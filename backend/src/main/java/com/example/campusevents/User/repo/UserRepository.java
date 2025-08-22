package com.example.campusevents.User.repo;


import com.example.campusevents.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);


    Optional<User> findByUsername(String username);

    // Find all verified users
    List<User> findByVerifiedTrue();

    // Find all users with a specific role
    List<User> findByRole(User.Role role);

    boolean existsByEmail(String email);


    boolean existsByUsername(String username);
}
