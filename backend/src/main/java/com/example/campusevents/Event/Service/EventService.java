package com.example.campusevents.Event.Service;

import com.example.campusevents.Event.DTO.FeedDTO;
import com.example.campusevents.Event.DTO.UpdateEventDTO;

import com.example.campusevents.Event.Controller.EventController;
import com.example.campusevents.Event.Model.Event;
import com.example.campusevents.Event.Repo.EventRepository;
import com.example.campusevents.User.Model.User;
import com.example.campusevents.User.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public Page<FeedDTO> getFeedWithLikeInfo(String username, int page, int size) {
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> events = eventRepository.findSortedActiveEvents(pageable);

        return events.map(event -> new FeedDTO(
                event,
                event.getLikedByUsers().contains(currentUser)
        ));
    }

    // Post a new event (check posting limits for unverified users)
    public Event createEvent(Event event, User user) {
        if (!user.isVerified()) {
            // Count user's events in the last 7 days
            LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
            long recentPosts = eventRepository.findAll().stream()
                    .filter(e -> e.getPostedBy().getId().equals(user.getId()))
                    .filter(e -> e.getDatePosted().isAfter(oneWeekAgo))
                    .count();

            if (recentPosts >= 3) {
                throw new IllegalStateException("Unverified users can only post up to 3 events per week.");
            }
        }
        event.setActive(true);

        event.setPostedBy(user);
        event.setDatePosted(LocalDateTime.now());

        System.out.println("Active before save: " + event.getActive());

        return eventRepository.save(event);
    }


    @Transactional
    public void likeEvent(Long eventId, User user) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        boolean alreadyLiked = eventRepository.existsByIdAndLikedByUsers_id(eventId, user.getId());
        if (alreadyLiked) {
            throw new IllegalStateException("You have already liked this event.");
        }
        event.getLikedByUsers().add(user);

    }

    @Transactional
    public void unlikeEvent(Long eventId, String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        User managedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!event.getLikedByUsers().contains(managedUser)) {
            throw new IllegalStateException("You have not liked this event.");
        }

        event.getLikedByUsers().remove(managedUser);


        eventRepository.save(event); // Optional, but helps ensure flush
    }



    // Deactivate expired events
    public void deactivatePastEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> pastActiveEvents = eventRepository.findByEventDateBeforeAndActiveTrue(now);

        if (!pastActiveEvents.isEmpty()) {
            pastActiveEvents.forEach(event -> event.setActive(false));
            eventRepository.saveAll(pastActiveEvents);
        }
    }




    public Event updateEvent(Long eventId, UpdateEventDTO dto, String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!event.getPostedBy().getUsername().equals(username)) {
            throw new SecurityException("You can only update your own events.");
        }

        if (dto.getName() != null) {
            event.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getImageUrl() != null) {
            event.setImageUrl(dto.getImageUrl());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getEventEndDate() != null) {
            event.setEventEndDate(dto.getEventEndDate());
        }
        if (dto.getLocation() != null) {
            event.setLocation(dto.getLocation());
        }
        if (dto.getType() != null) {
            event.setType(dto.getType());
        }

        return eventRepository.save(event);
    }


    public void deleteEvent(Long eventId, User user) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!event.getPostedBy().getId().equals(user.getId())) {
            throw new SecurityException("You can only delete your own events");
        }

        eventRepository.delete(event);
    }

}
