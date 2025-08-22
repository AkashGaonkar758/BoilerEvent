package com.example.campusevents.Event.DTO;

import com.example.campusevents.Event.Model.Event;

import java.time.LocalDateTime;

public class UpdateEventDTO {

    private String name;
    private String description;
    private String imageUrl;
    private LocalDateTime eventDate;
    private LocalDateTime eventEndDate;
    private String location;
    private Event.EventType type;

    public UpdateEventDTO(String name, String description, String imageUrl, LocalDateTime eventDate, LocalDateTime eventEndDate, String location, Event.EventType type) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.eventDate = eventDate;
        this.eventEndDate = eventEndDate;
        this.location = location;
        this.type = type;
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

    public Event.EventType getType() {
        return type;
    }

    public void setType(Event.EventType type) {
        this.type = type;
    }
}
