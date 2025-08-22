package com.example.campusevents.Event.Controller;


import com.example.campusevents.Event.DTO.EventResponseDTO;
import com.example.campusevents.Event.DTO.FeedDTO;
import com.example.campusevents.Event.DTO.UpdateEventDTO;
import com.example.campusevents.Event.Model.Event;
import com.example.campusevents.Event.Service.EventService;
import com.example.campusevents.User.Model.User;
import com.example.campusevents.User.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {


    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    // GET /events/feed




    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event, Principal principal) {
        try {
            String username = principal.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));


            if (event.getName() == null || event.getEventDate() == null || event.getEventEndDate() == null) {
                return ResponseEntity.badRequest().body("Missing required event fields.");
            }

            Event created = eventService.createEvent(event, user);
            return ResponseEntity.ok(toResponseDto(created));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create event: " + e.getMessage());
        }
    }

    @GetMapping("/feed")
    public Page<FeedDTO> getEventFeed(Principal principal,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size) {
        return eventService.getFeedWithLikeInfo(principal.getName(), page, size);
    }



    @PostMapping("/{id}/like")
    public ResponseEntity<Object> likeEvent(@PathVariable Long id, Principal principal) {
        try {
            String username = principal.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

            eventService.likeEvent(id, user);

            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException ex) {
            // for example: User not found or Event not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));

        } catch (IllegalStateException ex) {
            // for example: User already liked this event
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));

        } catch (Exception ex) {
            // for any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }


    @PostMapping("/{id}/unlike")
    public ResponseEntity<Object> unlikeEvent(@PathVariable Long id, Principal principal) {
        try {
            String username = principal.getName();

            eventService.unlikeEvent(id, username);

            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException ex) {
            // for example: User not found or Event not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));

        } catch (IllegalStateException ex) {
            // for example: User already liked this event
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));

        } catch (Exception ex) {
            // for any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEventDTO dto,
            Principal principal) {

        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        try {
            Event updated = eventService.updateEvent(id, dto, user.getUsername());
            return ResponseEntity.ok(toResponseDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id,
          Principal principal
    ) {
        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        eventService.deleteEvent(id, user);
        return ResponseEntity.noContent().build();
    }


    private EventResponseDTO toResponseDto(Event event) {
        return new EventResponseDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getImageUrl(),
                event.getEventDate(),
                event.getEventEndDate(),
                event.getLocation(),
                event.getType().name(),
                event.getLikedByUsers().size(),
                event.getPostedBy() != null ? event.getPostedBy().getUsername() : null
        );
    }



}
