package com.umbrella.insurance.core.models.users.events.eventTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {
    Optional<EventType> findEventTypeByEventTypeName(String eventTypeName);
    void deleteEventTypeByEventTypeName(String eventTypeName);
}
