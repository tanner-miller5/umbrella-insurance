package com.umbrella.insurance.core.models.users.events.eventTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.EventType;

import java.util.List;
import java.util.Optional;

public interface EventTypeService {
    EventType saveEventType(EventType eventType);
    List<EventType> getEventTypes();
    EventType updateEventType(EventType eventType);
    void deleteEventType(Long eventTypeId);
    Optional<EventType> getEventTypeByEventTypeName(String eventTypeName);
    void deleteEventTypeByEventTypeName(String eventTypeName);
    Optional<EventType> getEventTypeById(Long eventTypeId);
}
