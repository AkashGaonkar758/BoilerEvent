package com.example.campusevents.Event.DTO;

import com.example.campusevents.Event.Model.Event;
import com.example.campusevents.User.Model.User;

import java.time.LocalDateTime;

public class FeedDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private LocalDateTime eventDate;
    private String location;
    private int likeCount;
    private boolean likedByCurrentUser;
    private User postedBy;
    // constructor, getters, setters


    public FeedDTO(Long id, String name, String description, String imageUrl,  LocalDateTime eventDate,String location,  int likeCount, boolean likedByCurrentUser, User postedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.eventDate = eventDate;
        this.likeCount = likeCount;
        this.likedByCurrentUser = likedByCurrentUser;
        this.postedBy = postedBy;
    }

    public FeedDTO(Event event, boolean likedByCurrentUser) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.imageUrl = event.getImageUrl();
        this.location = event.getLocation();
        this.eventDate = event.getEventDate();
        this.likeCount = event.getLikedByUsers().size();
        this.likedByCurrentUser = likedByCurrentUser;
        this.postedBy = event.getPostedBy();
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public long getLikeCount() {
        return likeCount;
    }



    public boolean isLikedByCurrentUser() {
        return likedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        this.likedByCurrentUser = likedByCurrentUser;
    }
}
