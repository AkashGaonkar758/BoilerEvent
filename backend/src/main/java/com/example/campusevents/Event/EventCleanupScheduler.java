package com.example.campusevents.Event;


import com.example.campusevents.Event.Service.EventService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventCleanupScheduler {

    private final EventService eventService;

    public EventCleanupScheduler(EventService eventService) {
        this.eventService = eventService;
    }

    // Runs every day at midnight (server time)
    @Scheduled(cron = "0 0 0 * * *")
    public void deactivatePastEvents() {
        eventService.deactivatePastEvents();
    }
}
