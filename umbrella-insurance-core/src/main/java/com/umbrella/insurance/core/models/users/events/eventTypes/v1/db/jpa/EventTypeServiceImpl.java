package com.umbrella.insurance.core.models.users.events.eventTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventTypeServiceImpl implements EventTypeService {
    @Autowired
    EventTypeRepository eventTypeRepository;

    @Override
    public EventType saveEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    @Override
    public List<EventType> getEventTypes() {
        return eventTypeRepository.findAll();
    }

    @Override
    public EventType updateEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    @Override
    public void deleteEventType(Long eventTypeId) {
        eventTypeRepository.deleteById(eventTypeId);
    }

    @Override
    public Optional<EventType> getEventTypeByEventTypeName(String eventTypeName) {
        return eventTypeRepository.findEventTypeByEventTypeName(eventTypeName);
    }

    @Override
    public void deleteEventTypeByEventTypeName(String eventTypeName) {
        eventTypeRepository.deleteEventTypeByEventTypeName(eventTypeName);
    }

    @Override
    public Optional<EventType> getEventTypeById(Long eventTypeId) {
        return eventTypeRepository.findById(eventTypeId);
    }
}
