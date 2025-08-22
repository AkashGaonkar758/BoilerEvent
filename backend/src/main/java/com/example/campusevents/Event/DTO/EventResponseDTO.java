package com.example.campusevents.Event.DTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class EventResponseDTO {

    @NotBlank
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String imageUrl;
    private LocalDateTime eventDate;
    private LocalDateTime eventEndDate;
    private String location;
    @NotBlank
    private String type;
    private int likeCount;
    private String postedByUsername;

    public EventResponseDTO(Long id, String name, String description, String imageUrl, LocalDateTime eventDate, LocalDateTime eventEndDate, String location, String name1, int size, String s) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.eventDate = eventDate;
        this.eventEndDate = eventEndDate;
        this.location = location;
        this.name = name;
        this.likeCount = size;
        this.postedByUsername = s;

    }

    public EventResponseDTO() {
        super();


    }

    @NotBlank
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getPostedByUsername() {
        return postedByUsername;
    }

    public void setPostedByUsername(String postedByUsername) {
        this.postedByUsername = postedByUsername;
    }
}
