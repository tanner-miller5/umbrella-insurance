package com.umbrella.insurance.core.models.users.events.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> getEventBySessionId(Long sessionId);
    void deleteEventBySessionId(Long sessionId);
}
