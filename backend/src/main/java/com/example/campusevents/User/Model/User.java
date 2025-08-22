package com.example.campusevents.User.Model;
import com.example.campusevents.Event.Model.Event;
import com.example.campusevents.User.DTO.UserUpdateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name =  "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;


    @Column(nullable = false)
    private String lastName;


    @Column(nullable = false, unique = true)
    private String email;  // should be validated to be @purdue.edu on signup

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;  // store hashed password only

    @Enumerated(EnumType.STRING)
    private Year year;  // e.g. "Sophomore", "Senior", etc.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // e.g. ROLE_USER, ROLE_ADMIN

    @Column(nullable = false)
    private boolean verified;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // events this user has posted
    @OneToMany(mappedBy = "postedBy")
    private Set<Event> eventsPosted = new HashSet<>();

    // events this user has liked
    @ManyToMany(mappedBy = "likedByUsers")
    private Set<Event> likedEvents = new HashSet<>();



    public enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }

    public enum Year {
        FRESHMAN,
        SOPHOMORE,
        JUNIOR,
        SENIOR
    }


    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String username, String password, Year year, Role role, boolean verified, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.year = year;
        this.role = role;
        this.verified = verified;
        this.createdAt = createdAt;
    }




    @JsonIgnore
    public Set<Event> getEventsPosted() {
        return eventsPosted;
    }
    @JsonIgnore
    public void setEventsPosted(Set<Event> eventsPosted) {
        this.eventsPosted = eventsPosted;
    }
    @JsonIgnore
    public Set<Event> getLikedEvents() {
        return likedEvents;
    }
    @JsonIgnore
    public void setLikedEvents(Set<Event> likedEvents) {
        this.likedEvents = likedEvents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
