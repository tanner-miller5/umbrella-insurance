package com.umbrella.insurance.core.models.users.sessions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Session;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    Session saveSession(Session session);
    List<Session> getSessions();
    Session updateSession(Session session);
    void deleteSession(Long sessionId);
    Optional<Session> getSessionBySessionCode(String sessionCode);
    Optional<Session> getSessionByUserId(Long userId);
    void deleteSessionBySessionCode(String sessionCode);
    Optional<Session> getSessionBySessionId(Long sessionId);
    void deleteSessionByUserId(Long userId);
}
