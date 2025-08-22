package com.example.campusevents.Event.Model;
import com.example.campusevents.User.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;



import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1000)
    private String description;
    @Column
    private String imageUrl; // You can handle file uploads separately
    @Column(nullable = false)
    private LocalDateTime datePosted;
    @Column(nullable = false)
    private LocalDateTime eventDate;
    @Column(nullable = false)
    private LocalDateTime eventEndDate;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Column(name = "active", nullable = false)
    private boolean active;
    // Track users who liked the event
    // creates third table (Join Table)
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "event_likes",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedByUsers = new HashSet<>();

    // Who posted the event
    @ManyToOne
    @JoinColumn(name = "posted_by_id")
    private User postedBy;

    public Event() {
    }

    public Event(Long id, String name, String description, String imageUrl, LocalDateTime datePosted, LocalDateTime eventDate, LocalDateTime eventEndDate, String location, EventType type, boolean active, Set<User> likedByUsers, User postedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.datePosted = datePosted;
        this.eventDate = eventDate;
        this.eventEndDate = eventEndDate;
        this.location = location;

        this.type = type;
        this.active = active;
        this.likedByUsers = likedByUsers;
        this.postedBy = postedBy;
    }
    //happen before inserting event
    @PrePersist
    protected void onCreate() {
        this.datePosted = LocalDateTime.now();
        this.active = true;
    }



    public enum EventType {
        MEMBER_ONLY,
        OPEN_TO_ALL
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDateTime getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(LocalDateTime eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }
}
