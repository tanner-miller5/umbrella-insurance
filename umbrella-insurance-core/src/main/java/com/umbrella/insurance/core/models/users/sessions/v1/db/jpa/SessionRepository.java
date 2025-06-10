package com.umbrella.insurance.core.models.users.sessions.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.Session;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findBySessionCode(String sessionCode);
    @Query(value = "SELECT * FROM sessions s WHERE s.user_id = :userId ORDER BY s.start_date_time desc limit 1", nativeQuery = true)
    Optional<Session> findByUserId(Long userId);
    void deleteBySessionCode(String sessionCode);
    void deleteByUserId(Long userId);
}
