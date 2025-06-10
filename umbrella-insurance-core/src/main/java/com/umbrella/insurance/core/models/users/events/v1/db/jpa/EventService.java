package com.umbrella.insurance.core.models.users.events.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Event saveEvent(Event event);
    List<Event> getEvents();
    Event updateEvent(Event event);
    void deleteEvent(Long eventId);
    Optional<Event> getEventBySessionId(Long sessionId);
    Optional<Event> getEventById(Long eventId);
    void deleteBySessionId(Long sessionId);
}
