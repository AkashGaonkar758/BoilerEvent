package com.example.campusevents.Event.Repo;

import com.example.campusevents.Event.Model.Event;
import com.example.campusevents.User.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Custom feed: Active events sorted by likes, verified status, and event date
    @Query("SELECT e FROM Event e " +
            "WHERE e.active = true AND e.eventEndDate >= CURRENT_TIMESTAMP " +
            "ORDER BY SIZE(e.likedByUsers) DESC, e.postedBy.verified DESC, e.eventDate ASC")
    Page<Event> findSortedActiveEvents(Pageable pageable);

    // Get all active events (for feed)
    List<Event> findByActiveTrue();
    Optional<Event> findByName(String name);


    boolean existsByIdAndLikedByUsers_id(Long eventId, Long userId);;


    // Get events by type (e.g. MEMBER_ONLY or OPEN_TO_ALL)
    List<Event> findByType(Event.EventType type);

    // Get all events by a specific user
    List<Event> findByPostedBy(User user);


    List<Event> findByEventDateAfter(LocalDateTime now);


    List<Event> findByTypeAndActiveTrue(Event.EventType type);

    List<Event> findByEventDateBeforeAndActiveTrue(LocalDateTime date);
}